/*___________________________________________________________________________________________________

Title:		twi.c
Version:	3.0
_____________________________________________________________________________________________________*/


/* ----------------------------- LICENSE - GNU GPL v3 -----------------------------------------------

* This license must be included in any redistribution.

* Copyright (C) 2021 Liviu Istrate, www.programming-electronics-diy.xyz (istrateliviu24@yahoo.com)

* Project URL: https://www.programming-electronics-diy.xyz/2021/10/i2c-and-twi-two-wire-interface-library.html

* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.

* You should have received a copy of the GNU General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.

--------------------------------- END OF LICENSE --------------------------------------------------*/ 


//=========================================================================================
// INCLUDE
//=========================================================================================
#include "twi.h"


//=========================================================================================
// GLOBALS
//=========================================================================================

// Initialize TWI0 registers
#if TWI_ENABLE_TWI0 == 1
TWI_reg_t TWI0 = {
	#if defined(TWBR0) && defined(TWCR0)
		.TWI_TWBR = &TWBR0,
		.TWI_TWCR = &TWCR0,
		.TWI_TWSR = &TWSR0,
		.TWI_TWDR = &TWDR0,
		.TWI_TWAR = &TWAR0,
	#else
		.TWI_TWBR = &TWBR,
		.TWI_TWCR = &TWCR,
		.TWI_TWSR = &TWSR,
		.TWI_TWDR = &TWDR,
		.TWI_TWAR = &TWAR,
	#endif
};

TWI_t twi0 = {
	.twi_reg = &TWI0
};
#endif


// Initialize TWI1 registers
#if defined(TWBR1) && TWI_ENABLE_TWI1 == 1
TWI_reg_t TWI1 = {
	.TWI_TWBR = &TWBR1,
	.TWI_TWCR = &TWCR1,
	.TWI_TWSR = &TWSR1,
	.TWI_TWDR = &TWDR1,
	.TWI_TWAR = &TWAR1,
};

TWI_t twi1 = {
	.twi_reg = &TWI1
};
#endif


//=========================================================================================
// FUNCTIONS
//=========================================================================================
static void waitTWINTflag(TWI_t* twi){
	uint16_t timeout = TWI_TIMEOUT;
	
	while(!(*twi->twi_reg->TWI_TWCR & (1<<TWINT)) && (*twi->twi_reg->TWI_TWSR & 0xF8) != 0){
		if(timeout) timeout--;
		else break;
	}
}


/*-----------------------------------------------------------------------------------------
	Initialization function. Sets TWI bit rate and enables global interrupts.
 
	frequency	Desired TWI clock frequency in Hz 
				(typically 100 kHz for standard mode or 400 kHz for fast mode).
-------------------------------------------------------------------------------------------*/
void TWI_Init(TWI_t* twi, uint32_t frequency){
	twi->TWI_bytesReceived = 0;
	twi->TWI_RXreadIdx = 0;
	twi->TWI_RXwriteIdx = 0;
	
	// Set TWI bit rate
	*twi->twi_reg->TWI_TWBR = (F_CPU / (2 * frequency * TWI_PRESCALER)) - 8; // Set bit rate register
	
	// Enable global interrupts
	sei();
}


/*-----------------------------------------------------------------------------------------
	Set device address. If the LSB of TWARn is written to TWARn.TWGCI=1, the TWI will respond 
	to the general call address (0x00), otherwise it will ignore the general call address.
	The address '0000 000' is reserved for a general call.
	All addresses of the format '1111 xxx' should be reserved for future purposes.
 
	The Address Match unit is able to compare addresses even when the AVR MCU 
	is in sleep mode, enabling the MCU to wake up if addressed by a Master.
 
	general_call	if true, the first bit will be set to 1 and the TWI module
					will respond to the general call address 0x00.
-------------------------------------------------------------------------------------------*/
void TWI_SetAddress(TWI_t* twi, uint8_t addr, bool general_call){
	// LSB is reserved for general call
	*twi->twi_reg->TWI_TWAR = (addr << 1) | 1;
	
	if(general_call) *twi->twi_reg->TWI_TWAR |= 1;
}


/*-----------------------------------------------------------------------------------------
	Master mode only.
	Sends a START command. If an error occurs the TWI_ERROR_FLAG is set and the application 
	software might take some special action, like calling an error routine.
	The returned status code from TWI hardware is saved in the TWI_STATUS_CODE variable.
	After this function TWI_ContactDevice() must be used.
 
	After a repeated START condition (status code 0x10), the 2-wire Serial Interface can access the same
	Slave again, or a new Slave without transmitting a STOP condition. Repeated START enables the Master
	to switch between Slaves, Master Transmitter mode and Master Receiver mode without losing control of
	the bus.
 
	The START/STOP controller is able to detect START and STOP conditions even
	when the AVR MCU is in one of the sleep modes, enabling the MCU to wake up if 
	addressed by a Master.
-------------------------------------------------------------------------------------------*/
void TWI_StartTransmission(TWI_t* twi){
	twi->TWI_CHECK_STATUS = 0;
	
	// Send START condition, clear the flag just. When writing TWCRn, the TWINT bit should be set.
	*twi->twi_reg->TWI_TWCR = (1<<TWINT) | (1<<TWSTA) | (1<<TWEN);
	
	// Wait for TWINT Flag set.
	// This indicates that the START condition has been transmitted.
	waitTWINTflag(twi);
	// Save Status Register and mask pre-scaler bits.
	twi->TWI_STATUS_CODE = *twi->twi_reg->TWI_TWSR & 0xF8;
	
	// Check value of TWI Status Register.
	// If status is different from START set the error flag.
	if((twi->TWI_STATUS_CODE != TWI_CODE_MT_START) && (twi->TWI_STATUS_CODE != TWI_CODE_MT_START_R)){
		twi->TWI_CHECK_STATUS = 1;
	}
}


/*-----------------------------------------------------------------------------------------
	When TWARn and TWCRn have been initialized, the TWI waits until it is addressed 
	by its own slave address (or the general call address if enabled) followed by the 
	data direction bit. If the direction bit is 1 (read), the TWI will operate in ST mode, 
	otherwise SR mode is entered. After its own slave address and the write bit have been 
	received, the TWINT Flag is set and a valid status code can be read from TWSRb. 
	The status code is used to determine the appropriate software action. 
	The ST mode may also be entered if arbitration is lost while the TWI is in 
	the Master mode (see state 0xB0).
 
	While TWCRn.TWEA is zero, the TWI does not respond to its own slave address. 
	However, the 2-wire Serial Bus is still monitored and address recognition may resume 
	at any time by setting TWEA. This implies that the TWEA bit may be used to temporarily 
	isolate the TWI from the 2-wire Serial Bus.
-------------------------------------------------------------------------------------------*/
void TWI_SlaveMode(TWI_t* twi){
	// Clear the flag, enable TWI, enable address acknowledgment, enable interrupt to receive data.
	*twi->twi_reg->TWI_TWCR = (1<<TWINT) | (1<<TWEN) | (1<<TWEA) | (1<<TWIE);
}


/*-----------------------------------------------------------------------------------------
	Master mode only.
	Sends the SLA+RW packet. If an error occurs the TWI_ERROR_FLAG is set.
	The returned status code from TWI hardware is saved in the TWI_STATUS_CODE variable.
	The TWEA bit is set to generate the acknowledge pulse.
			
	address					The 7-bit address of the device.
							The first 7 most significant bits corresponds to the device address while
							the first bit represents R/W.
	
	rw						Read or write mode. In read mode the TWI interrupt will be enabled and the 
							received data can be accessed using TWI_ReadByte().
							Can be one of the following flags: TWI_READ_MODE, TWI_WRITE_MODE
-------------------------------------------------------------------------------------------*/
void TWI_ContactDevice(TWI_t* twi, uint8_t address, uint8_t rw){
	uint8_t sla_ack_code;
	twi->TWI_CHECK_STATUS = 0;
	address <<= 1;
	
	// Write mode - Master Transmitter (MT)
	if(rw == TWI_WRITE_MODE){
		sla_ack_code = TWI_CODE_MT_SLA_ACK;
		
	// Read mode - Master Receiver (MR)
	}else{
		sla_ack_code = TWI_CODE_MR_SLA_ACK;
		address = address | 1;
	}
	
	// Load SLA_W into TWDR Register.
	// TWDR must only be written when TWINT is high.
	// Clear TWINT bit in TWCR to start transmission of address.
	*twi->twi_reg->TWI_TWDR = address;
	*twi->twi_reg->TWI_TWCR = (1<<TWINT) | (1<<TWEN) | (1<<TWEA);
	
	// Wait for TWINT Flag set. This indicates that
	// the SLA+RW has been transmitted, and ACK/NACK has been received.
	waitTWINTflag(twi);
	twi->TWI_STATUS_CODE = *twi->twi_reg->TWI_TWSR & 0xF8;

	// Check value of TWI Status Register. Mask pre-scaler bits.
	// If status is different from MT_SLA_ACK or MR_SLA_ACK set the error flag.
	if((twi->TWI_STATUS_CODE) != sla_ack_code){
		twi->TWI_CHECK_STATUS = 1;
	}
	
	// Enable interrupt to receive the data
	if(rw == TWI_READ_MODE) *twi->twi_reg->TWI_TWCR |= 1 << TWIE;
}



/*-----------------------------------------------------------------------------------------
	Transmit a string of bytes. If the receiver sends a NACK the rest of the bytes, if any, 
	will not be transmitted. If status code is not ACK the error flag will be set and rest
	of bytes will not be transmitted.
			
	data					Pointer to a string
-------------------------------------------------------------------------------------------*/
void TWI_Transmit(TWI_t* twi, const uint8_t *data){	
	while(*data != '\0'){
		TWI_TransmitByte(twi, *data);
		data++;
		if(twi->TWI_CHECK_STATUS) break;
	}
}


/*-----------------------------------------------------------------------------------------
	Transmit a single byte. If status code is not ACK the error flag will be set.
 
	This is repeated until the last byte has been sent and the transfer is ended, 
	either by generating a STOP condition or by a repeated START.
			
	data				byte to send
-------------------------------------------------------------------------------------------*/
void TWI_TransmitByte(TWI_t* twi, uint8_t byte_data){
	twi->TWI_CHECK_STATUS = 0;

	// Load DATA into TWDR Register.
	// TWDR must only be written when TWINT is high.
	// Clear TWINT bit in TWCR to start transmission of data
	*twi->twi_reg->TWI_TWDR = byte_data;
	*twi->twi_reg->TWI_TWCR = (1<<TWINT) | (1<<TWEN) | (1<<TWEA); // TWEA needed in Slave mode

	// Wait for TWINT Flag set. This indicates that the
	// DATA has been transmitted, and ACK/NACK has been received.
	waitTWINTflag(twi);
	twi->TWI_STATUS_CODE = *twi->twi_reg->TWI_TWSR & 0xF8;

	// Check value of TWI Status Register.
	// If status is not ACK stop sending more data.
	if((twi->TWI_STATUS_CODE != TWI_CODE_MT_DATA_ACK) && (twi->TWI_STATUS_CODE != TWI_CODE_ST_DATA_OUT_ACK)){
		// If status code is not ACK set the error flag
		twi->TWI_CHECK_STATUS = 1;
	}
}


/*-----------------------------------------------------------------------------------------
	Issue a STOP command to end the transmission.
	In Slave mode, setting the TWSTO bit can be used to recover from an error condition. 
	This will not generate a STOP condition, but the TWI n returns to a well-defined 
	un-addressed Slave mode and releases the SCL and SDA lines to a high impedance state.
-------------------------------------------------------------------------------------------*/
void TWI_StopTransmission(TWI_t* twi){
	waitTWINTflag(twi);
	
	// Transmit STOP condition
	*twi->twi_reg->TWI_TWCR = (1<<TWINT) | (1<<TWEN) | (1<<TWSTO);
}


/*-----------------------------------------------------------------------------------------
	Disables the TWI. Any ongoing transmissions will be stopped immediately.
	Using TWI_StartTransmission() will re-enable the TWI module.
-------------------------------------------------------------------------------------------*/
void TWI_Disable(TWI_t* twi){
	*twi->twi_reg->TWI_TWCR = 0;
}


/*-----------------------------------------------------------------------------------------
	Returns true if new bytes are available.
-------------------------------------------------------------------------------------------*/
bool TWI_ByteReady(TWI_t* twi){
	return (twi->TWI_bytesReceived != 0);
}


/*-----------------------------------------------------------------------------------------
	Returns true if a master asks for data using SLA+R.
	Set by interrupt on TWI_CODE_ST_SLA_ACK.
-------------------------------------------------------------------------------------------*/
bool TWI_DataRequest(TWI_t* twi){
	if(twi->TWI_DATA_REQUEST){
		twi->TWI_DATA_REQUEST = 0;
		return true;
	}
	
	return false;
}


/*-----------------------------------------------------------------------------------------
	Returns a received byte if available. Every time the function is executed the next 
	received byte is returned if available.
-------------------------------------------------------------------------------------------*/
uint8_t TWI_ReadByte(TWI_t* twi){
	uint8_t received_byte = '\0';
	
	// TWI received at least 1 byte
	if(twi->TWI_bytesReceived){
		// Reading of bytes has reached the end of buffer so start from 0 again
		if(twi->TWI_RXreadIdx >= TWI_RX_BUFFER_SIZE){
			twi->TWI_RXreadIdx = 0;
		}

		received_byte = twi->TWI_RX_BUFFER[twi->TWI_RXreadIdx];
		twi->TWI_RXreadIdx++;
		
		ATOMIC_BLOCK(ATOMIC_RESTORESTATE){
			twi->TWI_bytesReceived--;
		}
	}
	
	return received_byte;
}


/*-----------------------------------------------------------------------------------------
	Returns the error flag [0:1] in case the TWI status code is other than ACK.
-------------------------------------------------------------------------------------------*/
uint8_t TWI_StatusNotACK(TWI_t* twi){
	uint8_t tmp = twi->TWI_CHECK_STATUS;
	twi->TWI_CHECK_STATUS = 0;
	return tmp;
}


/*-----------------------------------------------------------------------------------------
	Returns the last TWI status code. Can be used when the error flag is 1. 
	The TWI status codes are defined in the header file.
-------------------------------------------------------------------------------------------*/
uint8_t TWI_ReadStatusCode(TWI_t* twi){
	return twi->TWI_STATUS_CODE;
}


/*-----------------------------------------------------------------------------------------
	Resets the TWI interface by sending a START, 9 of 1's another START and a STOP, 
	in case an error appeared on the bus. The explanation for this is a bit complex 
	and can be found is some data sheets for example the data sheet for MCP4706 DAC page 70.
-------------------------------------------------------------------------------------------*/
#if TWI_USE_INTERFACE_RESET == 1
void TWI_ResetTWIInterface(TWI_t* twi){
	uint8_t i = 0;
	
	TWI_StartTransmission(twi);
	
	// This must be set before disabling the TWI
	TWI_SCL_PORT &= ~(1 << TWI_SCL_PIN);
	TWI_SCL_DDR |= (1 << TWI_SCL_PIN);
	
	// Set the data bit to a 1
	TWI_SDA_PORT |= (1 << TWI_SDA_PIN);
	TWI_SDA_DDR |= (1 << TWI_SDA_PIN);
	
	TWI_Disable(twi);
	
	// Send 9 bits of 1. i<10 is correct, checked using a logic analyzer.
	for(i=0; i<10; i++){
		// Clock the SCL line at 100kHz
		TWI_SCL_PORT &= ~(1 << TWI_SCL_PIN);
		_delay_us(5);
		TWI_SCL_PORT |= (1 << TWI_SCL_PIN);
		_delay_us(5);
	}
	
	TWI_StartTransmission(twi);
	TWI_StopTransmission(twi);
}
#endif



//=========================================================================================
// ISR
//=========================================================================================
/* 
The TWSRn only contains relevant status information when the TWI Interrupt Flag is asserted. 
At all other times, the TWSRn contains a special status code indicating that no relevant 
status information is available. As long as the TWINT Flag is set, the SCL line is held low. 
This allows the application software to complete its tasks before allowing the 
TWI transmission to continue.
 */

#if TWI_ENABLE_TWI0 == 1
ISR(TWI0_vect){
	uint8_t twsr = *twi0.twi_reg->TWI_TWSR & 0xF8;
	
	// Data byte has been received; ACK/NACK has been received
	switch(twsr){
		case TWI_CODE_MR_DATA_IN_ACK:
		case TWI_CODE_MR_DATA_IN_NACK:
		case TWI_CODE_SR_DATA_IN_ACK:
		case TWI_CODE_SR_DATA_IN_NACK:
		case TWI_CODE_SR_GCALL_DATA_IN_ACK:
		case TWI_CODE_SR_GCALL_DATA_IN_NACK:
			// If the buffer is full start from 0
			if(twi0.TWI_RXwriteIdx >= TWI_RX_BUFFER_SIZE){
				twi0.TWI_RXwriteIdx = 0;
			}

			twi0.TWI_RX_BUFFER[twi0.TWI_RXwriteIdx] = *twi0.twi_reg->TWI_TWDR;
			twi0.TWI_RXwriteIdx++;
			twi0.TWI_bytesReceived++;
		break;
		
		case TWI_CODE_ST_SLA_ACK:
		case TWI_CODE_ST_AR_LOST:
			twi0.TWI_DATA_REQUEST = 1;
			
			// The interrupt is enabled in slave mode to notify the application
			// when a Master addresses the device. If not disabled here, the TWI module 
			// will send same data continuously.
			// Even so, it will send a byte (7) after address ACK.
			*twi0.twi_reg->TWI_TWCR &= ~(1<<TWIE);
		break;
		
		default:
			twi0.TWI_CHECK_STATUS = 1;
			twi0.TWI_STATUS_CODE = twsr;
		break;
	}
	
	// Clear the flag
	*twi0.twi_reg->TWI_TWCR |= (1<<TWINT);
}
#endif


#if defined(TWI1_vect) && TWI_ENABLE_TWI1 == 1
ISR(TWI1_vect){
	uint8_t twsr = *twi1.twi_reg->TWI_TWSR & 0xF8;
	
	// Data byte has been received; ACK/NACK has been received
	switch(twsr){
		case TWI_CODE_MR_DATA_IN_ACK:
		case TWI_CODE_MR_DATA_IN_NACK:
		case TWI_CODE_SR_DATA_IN_ACK:
		case TWI_CODE_SR_DATA_IN_NACK:
		case TWI_CODE_SR_GCALL_DATA_IN_ACK:
		case TWI_CODE_SR_GCALL_DATA_IN_NACK:
			// If the buffer is full start from 0
			if(twi1.TWI_RXwriteIdx >= TWI_RX_BUFFER_SIZE){
				twi1.TWI_RXwriteIdx = 0;
			}

			twi1.TWI_RX_BUFFER[twi1.TWI_RXwriteIdx] = *twi1.twi_reg->TWI_TWDR;
			twi1.TWI_RXwriteIdx++;
			twi1.TWI_bytesReceived++;
		break;
		
		case TWI_CODE_ST_SLA_ACK:
		case TWI_CODE_ST_AR_LOST:
			twi1.TWI_DATA_REQUEST = 1;
			
			// The interrupt is enabled in slave mode to notify the application
			// when a Master addresses the device. If not disabled here, the TWI module 
			// will send same data continuously.
			// Even so, it will send a byte (7) after address ACK.
			*twi1.twi_reg->TWI_TWCR &= ~(1<<TWIE);
		break;
		
		default:
			twi1.TWI_CHECK_STATUS = 1;
			twi1.TWI_STATUS_CODE = twsr;
		break;
	}
	
	// Clear the flag
	*twi1.twi_reg->TWI_TWCR |= (1<<TWINT);
}
#endif