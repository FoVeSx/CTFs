// avr-gcc -mmcu=atmega328p -DF_CPU=16000000UL -Os -o test flashdump.c twi.c

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include "twi.h"
#include <avr/io.h>
#include <util/delay.h>


#define EEPROM_ADDR 0x54
#define BAUDRATE 115200
#define BAUD_PRESCALLER (((F_CPU / (BAUDRATE * 16UL))) - 1)


// Initialize UART (configure the baud rate)
void uart_init() {
    UBRR0H = (uint8_t)(BAUD_PRESCALLER >> 8);
    UBRR0L = (uint8_t)(BAUD_PRESCALLER);

    UCSR0B = (1 << TXEN0); 
    UCSR0C = (1 << UCSZ01) | (1 << UCSZ00);
}

// Wait for empty transit buffer put data into the buffer to send that data
void uart_putc(uint8_t c) {
    while (!(UCSR0A & (1 << UDRE0)));
    UDR0 = c;
}

uint8_t TWI_ReadByte_NACK(TWI_t* twi) {
    *twi->twi_reg->TWI_TWCR = (1 << TWINT) | (1 << TWEN); 
    while (!(*twi->twi_reg->TWI_TWCR & (1 << TWINT)));
    return *twi->twi_reg->TWI_TWDR;
}


uint8_t eeprom_read(uint16_t addr) {
    TWI_StartTransmission(&twi0);
    TWI_ContactDevice(&twi0, addr, TWI_READ_MODE);
    return TWI_ReadByte_NACK(&twi0);
}

int main(void) {
    uart_init();

    for (uint16_t i = 0; i < 4196; i++) {
        uart_putc(eeprom_read(EEPROM_ADDR));
    }

    TWI_StopTransmission(&twi0);
}
