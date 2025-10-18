import struct

# Constants
START_SEED = 1714536000  # Approx. May 2024 Unix timestamp
END_SEED = 1717214400  # Approx. June 2024 Unix timestamp

# Load encrypted flag
with open("flag.enc", "rb") as f:
    encrypted_data = bytearray(f.read())

# PRNG Implementation (matches C version)
def srand_w(seed):
    return seed - 1

def rand_w(seed):
    return ((0x5851F42D4C957F2D * seed + 1) >> 33) & 0xFFFFFFFF

def xor_decrypt(data, seed):
    seed = srand_w(seed)
    decrypted = bytearray(data)
    for i in range(len(decrypted)):
        seed = rand_w(seed)
        decrypted[i] ^= seed % 127
    return decrypted

# Brute-force seed values
for seed in range(START_SEED, END_SEED + 1):
    decrypted = xor_decrypt(encrypted_data, seed)
    
    if decrypted.startswith(b"PWNME{"):  # Check for flag pattern
        print(f"[+] Found seed: {seed}")
        print("[+] Decrypted flag:", decrypted.decode(errors="replace"))
        break
