#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include <avr/io.h>
#include <util/delay.h>

void uart_init(void) {
    UBRR0 = F_CPU/16/115200UL - 1;
    UCSR0A = 0;
    UCSR0B = (1<<TXEN0);
    UCSR0C = (1<<UCSZ01)|(1<<UCSZ00);
}

void uart_puts(const char* s) {
    while (*s) {
        while (!(UCSR0A & (1<<UDRE0)));
        UDR0 = *s++;
    }
}

int main(void) {
    uart_init();
    _delay_ms(100);
    uart_puts("hello uart\n");
    while (1);
}
