import time
import random

def decrypt_with_seed(data, seed):
    random.seed(seed)  # Set seed
    decrypted = bytearray()
    for byte in data:
        key = random.randint(0, 0x7f)  # Generate same random byte sequence
        decrypted.append(byte ^ key)  # XOR decrypt
    return decrypted

def brute_force_decrypt(filename):
    with open(filename, "rb") as f:
        encrypted_data = f.read()

    start_time = int(time.mktime(time.strptime("2024-05-01 00:00:00", "%Y-%m-%d %H:%M:%S")))
    end_time = int(time.mktime(time.strptime("2024-05-31 23:59:59", "%Y-%m-%d %H:%M:%S")))

    for timestamp in range(start_time, end_time):
        decrypted_data = decrypt_with_seed(encrypted_data, timestamp)
        decrypted_text = decrypted_data.decode(errors="ignore")

        if "PWNME{" in decrypted_text:
            print(f"[+] Found seed: {timestamp} ({time.ctime(timestamp)})")
            print(f"[+] Decrypted flag: {decrypted_text}")
            return

    print("[-] No valid flag found.")

if __name__ == "__main__":
    brute_force_decrypt("flag.enc")

