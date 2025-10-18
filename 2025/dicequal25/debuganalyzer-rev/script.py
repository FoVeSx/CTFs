#!/usr/bin/env python3
import sys

def extract_flag(filename):
    with open(filename, "rb") as f:
        data = f.read()

    flag_chars = []
    i = 0
    while i < len(data) - 2:
        if data[i] == 0x51:
            # Take the byte two positions after 0x51 as the flag character.
            flag_chars.append(chr(data[i+2]))
            # You can increment i by 1 or more if you want to avoid overlapping matches.
            i += 1
        else:
            i += 1

    flag = "".join(flag_chars)
    print("Extracted flag:", flag)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: {} <filename>".format(sys.argv[0]))
        sys.exit(1)
    extract_flag(sys.argv[1])

