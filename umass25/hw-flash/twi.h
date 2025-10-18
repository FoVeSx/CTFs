/*___________________________________________________________________________________________________

Title:
	twi.h v3.0

Description:
	Library for TWI protocol on AVR devices
	
	For complete details visit:
	https://www.programming-electronics-diy.xyz/2021/10/i2c-and-twi-two-wire-interface-library.html

Author:
 	Liviu Istrate
	istrateliviu24@yahoo.com
	www.programming-electronics-diy.xyz

Donate:
	Software development takes time and effort so if you find this useful consider a small donation at:
	paypal.me/alientransducer
_____________________________________________________________________________________________________*/


/* ----------------------------- LICENSE - GNU GPL v3 -----------------------------------------------

* This license must be included in any redistribution.

* Copyright (c) 2021 Liviu Istrate, www.programming-electronics-diy.xyz (istrateliviu24@yahoo.com)

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

/*___________________________________CHANGELOG________________________________________________________
 
v3.0 (9-04-2025)
    - Added support for Slave mode.
    - Fixed some issues with status codes.
 
v2.0 (1-01-2025)
    - Now devices with multiple TWI modules can use them simultaneously by using object pointers 
    as function parameters.
    - Some code optimization.
v1.2
    - Fixed a bug on ATmega328P and similar devices with only one I2C module where registers TWxRn
    are not defined.
_____________________________________________________________________________________________________*/

#ifndef TWI_H
#define TWI_H

//=========================================================================================
//  INCLUDES
//=========================================================================================
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/atomic.h>
#include <util/delay.h>
#include <stdbool.h>


//=========================================================================================
//  USER SETUP SECTION
//=========================================================================================

// Pins that correspond to TWI module. Only necessary if the TWI interface reset function is used.
// The pins are used to bit-bang 9 of 1's. Set USE_INTERFACE_RESET to 1 if the pins are defined
// or 0 if they are not.
#define TWI_USE_INTERFACE_RESET			0

// SDA and SCL pin location
#define TWI_SDA_DDR				DDRC
#define TWI_SDA_PORT				PORTC
#define TWI_SDA_PIN				PC4

#define TWI_SCL_DDR				DDRC
#define TWI_SCL_PORT				PORTC
#define TWI_SCL_PIN				PC5

// When both TWI modules (if available) are needed,
// set the defines to 1. By default the TWI module 1 is excluded
// so the ISR is not included to save space. Even if not used
// the compiler will still include the ISR functions.
#define TWI_ENABLE_TWI0				1
#define TWI_ENABLE_TWI1				0


//=========================================================================================
//  SYSTEM SETTINGS
//=========================================================================================
// Size of the receive buffer in bytes
#define TWI_RX_BUFFER_SIZE			10

#define CYCLES_PER_US				(F_CPU / 1000000.0) // CPU cycles per microsecond
#define TWI_TIMEOUT				(CYCLES_PER_US * 50); // ~600us

// TWI register structure
typedef struct {
    volatile uint8_t* TWI_TWBR;
    volatile uint8_t* TWI_TWCR;
    volatile uint8_t* TWI_TWSR;
    volatile uint8_t* TWI_TWDR;
    volatile uint8_t* TWI_TWAR;
} TWI_reg_t;


// TWI structure
typedef struct {
    TWI_reg_t* twi_reg;
   
    volatile uint8_t TWI_RX_BUFFER[TWI_RX_BUFFER_SIZE];
    volatile uint8_t TWI_bytesReceived;
    volatile uint8_t TWI_RXreadIdx;
    volatile uint8_t TWI_RXwriteIdx;
    
    uint8_t TWI_CHECK_STATUS;
    uint8_t TWI_STATUS_CODE;
    uint8_t TWI_DATA_REQUEST;
} TWI_t;


// TWI objects
extern TWI_t twi0;
extern TWI_t twi1;


//=========================================================================================
//  CONSTANTS
//=========================================================================================
#define TWI_400KHZ				400000 // Hz
#define TWI_100KHZ				100000 // Hz
#define TWI_PRESCALER				1 // [1, 4, 16, 64]
#define TWI_READ_MODE				1
#define TWI_WRITE_MODE				0
#define TWI_MASTER_MODE				0
#define TWI_SLAVE_MODE				1
#define TWI_GENERAL_CALL			0x00

//=========================================================================================
//  STATUS CODES
//=========================================================================================

// All the status codes mentioned in this section assume that the prescaler bits are zero or are masked to zero.
// Status Codes for Master Transmitter Mode
#define TWI_CODE_MT_START			0x08 // A START condition has been transmitted
#define TWI_CODE_MT_START_R			0x10 // A repeated START condition has been transmitted
#define TWI_CODE_MT_SLA_ACK			0x18 // SLA+W has been transmitted; ACK has been received
#define TWI_CODE_MT_SLA_NACK			0x20 // SLA+W has been transmitted; NOT ACK has been received
#define TWI_CODE_MT_DATA_ACK			0x28 // Data byte has been transmitted; ACK has been received
#define TWI_CODE_MT_DATA_NACK			0x30 // Data byte has been transmitted; NOT ACK has been received
#define TWI_CODE_MT_AR_LOST			0x38 // Arbitration lost in SLA+W or data bytes

// Status codes for Master Receiver Mode
#define TWI_CODE_MR_AR_LOST_OR_NACK		0x38 // Arbitration lost in SLA+R or NOT ACK bit
#define TWI_CODE_MR_SLA_ACK			0x40 // SLA+R has been transmitted; ACK has been received
#define TWI_CODE_MR_SLA_NACK			0x48 // SLA+R has been transmitted; NOT ACK has been received
#define TWI_CODE_MR_DATA_IN_ACK			0x50 // Data byte has been received; ACK has been returned
#define TWI_CODE_MR_DATA_IN_NACK		0x58 // Data byte has been received; NOT ACK has been returned

// Status Codes for Slave Transmitter Mode
#define TWI_CODE_ST_SLA_ACK			0xA8 // Own SLA+R has been received; ACK has been returned
#define TWI_CODE_ST_AR_LOST			0xB0 // Arbitration lost in SLA+R/W as Master; own SLA+R has been received; ACK has been returned
#define TWI_CODE_ST_DATA_OUT_ACK		0xB8 // Data byte in TWDRn has been transmitted; ACK has been received
#define TWI_CODE_ST_DATA_OUT_NACK		0xC0 // Data byte in TWDRn has been transmitted; NOT ACK has been received
#define TWI_CODE_ST_LAST_BYTE_ACK		0xC8 // Last data byte in TWDRn has been transmitted (TWEA = “0”); ACK has been received

// Status Codes for Slave Receiver Mode
#define TWI_CODE_SR_SLA_ACK			0x60 // Own SLA+W has been received; ACK has been returned
#define TWI_CODE_SR_AR_LOST			0x68 // Arbitration lost in SLA+R/W as Master; own SLA+W has been received; ACK has been returned
#define TWI_CODE_SR_GENERAL_CALL_ACK		0x70 // General call address has been received; ACK has been returned
#define TWI_CODE_SR_AR_LOST_GCALL		0x78 // Arbitration lost in SLA+R/W as Master; General call address has been received; ACK has been returned
#define TWI_CODE_SR_DATA_IN_ACK			0x80 // Previously addressed with own SLA+W; data has been received; ACK has been returned
#define TWI_CODE_SR_DATA_IN_NACK		0x88 // Previously addressed with own SLA+W; data has been received; NOT ACK has been returned
#define TWI_CODE_SR_GCALL_DATA_IN_ACK		0x90 // Previously addressed with general call; data has been received; ACK has been returned
#define TWI_CODE_SR_GCALL_DATA_IN_NACK		0x98 // Previously addressed with general call; data has been received; NOT ACK has been returned
#define TWI_CODE_SR_ADDR_STOP_START		0xA0 // A STOP condition or repeated START condition has been received while still addressed as Slave

// Miscellaneous States
// Status 0xF8 indicates that no relevant information is available because the TWINT Flag is not set. 
// This occurs between other states, and when the TWI is not involved in a serial transfer.
#define TWI_CODE_FLAG_NOT_SET			0xF8 // No relevant state information available; TWINT = “0”

// Status 0x00 indicates that a bus error has occurred during a 2-wire Serial Bus transfer. A bus error occurs
// when a START or STOP condition occurs at an illegal position in the format frame. Examples of such
// illegal positions are during the serial transfer of an address byte, a data byte, or an acknowledge bit.
// When a bus error occurs, TWINT is set. To recover from a bus error, the TWSTO Flag must set and
// TWINT must be cleared by writing a logic one to it. This causes the TWI to enter the not addressed Slave
// mode and to clear the TWSTO Flag (no other bits in TWCRn are affected). The SDA and SCL lines are
// released, and no STOP condition is transmitted.
#define TWI_CODE_BUS_ERROR			0x00 // Bus error due to an illegal START or STOP condition


//=========================================================================================
//  FUNCTION PROTOTYPES
//=========================================================================================
void TWI_Init(TWI_t* twi, uint32_t frequency);
void TWI_SetAddress(TWI_t* twi, uint8_t addr, bool general_call);
void TWI_StartTransmission(TWI_t* twi);
void TWI_SlaveMode(TWI_t* twi);
void TWI_ContactDevice(TWI_t* twi, uint8_t address, uint8_t rw);
void TWI_Transmit(TWI_t* twi, const uint8_t *data);
void TWI_TransmitByte(TWI_t* twi, uint8_t byte_data);
void TWI_StopTransmission(TWI_t* twi);
void TWI_Disable(TWI_t* twi);
bool TWI_ByteReady(TWI_t* twi);
bool TWI_DataRequest(TWI_t* twi);
uint8_t TWI_ReadByte(TWI_t* twi);
uint8_t TWI_StatusNotACK(TWI_t* twi);
uint8_t TWI_ReadStatusCode(TWI_t* twi);

#if TWI_USE_INTERFACE_RESET == 1
void TWI_ResetTWIInterface(TWI_t* twi);
#endif

#endif