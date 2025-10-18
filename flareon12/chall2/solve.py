from arc4 import ARC4

LEAD_RESEARCHER_SIGNATURE = b'm\x1b@I\x1dAoe@\x07ZF[BL\rN\n\x0cS'
ENCRYPTED_CHIMERA_FORMULA = b'r2b-\r\x9e\xf2\x1fp\x185\x82\xcf\xfc\x90\x14\xf1O\xad#]\xf3\xe2\xc0L\xd0\xc1e\x0c\xea\xec\xae\x11b\xa7\x8c\xaa!\xa1\x9d\xc2\x90'

# signature_byte = username_byte ^ (index + 42)
# username_byte = signature_byte ^ (index + 42)
required_username_bytes = bytes([
    c ^ (i + 42) for i, c in enumerate(LEAD_RESEARCHER_SIGNATURE)
])
required_username = required_username_bytes.decode()

print(f"✅ Discovered username: {required_username}\n")

# use username as the key to decrypt the formula
# disassembly shows it uses ARC4 with the username as the key
arc4_cipher = ARC4(required_username_bytes)
decrypted_formula = arc4_cipher.decrypt(ENCRYPTED_CHIMERA_FORMULA)

print("✨ --- SECRET CHIMERA FORMULA --- ✨")
print(decrypted_formula.decode())
print("✨ ------------------------------ ✨")