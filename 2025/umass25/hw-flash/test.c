#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include <avr/io.h>
#include <util/delay.h>

// I²C address of 24C64B (A2,A1,A0 = GND)
#define EEPROM_ADDR 0x54

// — UART @9600, 8N1 -------------------------------------------------
static void uart_init(void) {
    // UBRR0 = F_CPU/16/BAUD-1
    UBRR0 = F_CPU/16/9600UL - 1;
    UCSR0A = 0;
    UCSR0B = (1<<TXEN0);                  // enable TX
    UCSR0C = (1<<UCSZ01)|(1<<UCSZ00);     // 8N1
}

static void uart_putc(uint8_t c) {
    while (!(UCSR0A & (1<<UDRE0)));
    UDR0 = c;
}

// — TWI (I²C) at 100 kHz ---------------------------------------------
static void twi_init(void) {
    TWSR = 0;             // prescaler = 1
    // SCLfreq = F_CPU/(16 + 2*TWBR*prescaler)
    TWBR = 72;            // → ~100 kHz
}

static uint8_t twi_start(uint8_t sla_rw) {
    TWCR = (1<<TWINT)|(1<<TWSTA)|(1<<TWEN);
    while (!(TWCR & (1<<TWINT)));
    TWDR = sla_rw;
    TWCR = (1<<TWINT)|(1<<TWEN);
    while (!(TWCR & (1<<TWINT)));
    return (TWSR & 0xF8);
}

static void twi_write(uint8_t data) {
    TWDR = data;
    TWCR = (1<<TWINT)|(1<<TWEN);
    while (!(TWCR & (1<<TWINT)));
}

static uint8_t twi_read_nack(void) {
    TWCR = (1<<TWINT)|(1<<TWEN);  // NACK on last byte
    while (!(TWCR & (1<<TWINT)));
    return TWDR;
}

static void twi_stop(void) {
    TWCR = (1<<TWINT)|(1<<TWEN)|(1<<TWSTO);
    _delay_us(10);
}

// — Read one byte from two‑byte address, send it out UART ----------
static void read_eeprom_byte(uint16_t addr) {
    twi_start((EEPROM_ADDR<<1)|0);     // SLA+W
    twi_write(addr >> 8);              // MSB of address
    twi_write(addr & 0xFF);            // LSB of address
    twi_start((EEPROM_ADDR<<1)|1);     // SLA+R
    uint8_t b = twi_read_nack();
    twi_stop();
    uart_putc(b);
}

int main(void) {
    uart_init();
    twi_init();

    for (uint16_t addr = 0; addr < 8192; addr++) {
        read_eeprom_byte(addr);
    }
    // done — hang
    while (1) {}
}

uint8_t TWI_ReadByte_NACK(TWI_t* twi) {
    *twi->twi_reg->TWI_TWCR = (1 << TWINT) | (1 << TWEN); 
    while (!(*twi->twi_reg->TWI_TWCR & (1 << TWINT)));
    return *twi->twi_reg->TWI_TWDR;
}


uint8_t eeprom_read(uint16_t addr) {
    TWI_StartTransmission(&twi0);
    TWI_ContactDevice(&twi0, EEPROM_ADDR, TWI_READ_MODE);
    return TWI_ReadByte_NACK(&twi0);
}