import pikepdf
from PIL import Image
import io

with open('pretty_devilish_file.pdf', "rb") as pdf:
    contents = pdf.read()
    print("[!] PDF DUMP")
    print(contents)

print()
print()

with pikepdf.open('pretty_devilish_file.pdf') as pdf:
    print("[!] PIKEPDF DUMP")
    num_pages = len(pdf.pages)
    print(f"Number of pages: {num_pages}")

    first_page = pdf.pages[0]
    print(f"First page object: {first_page}")

    content_stream = first_page.Contents
    decompressed_data = content_stream.read_bytes()
    #print(decompressed_data)

"""
# The Hidden Inline Image 🖼️
That block of code starting with BI and ending with EI is embedding an image directly into the page's content stream. Let's look at its properties:

BI: Begin Inline Image.

/W 37 /H 1: The image has a Width of 37 pixels and a Height of 1 pixel. This is a massive clue. A 1-pixel-high image is essentially a line of data, perfect for hiding a string.

/F [/AHx /DCT]: These are the Filters. The data is first decoded from ASCII Hex (/AHx), and the result of that is then decoded as a JPEG image (/DCT).

ID ... EI: The hex data for the JPEG is between the Image Data and End Image operators.

The solution is to extract this 37x1 pixel JPEG, get the grayscale value of each of the 37 pixels, and interpret those values as ASCII characters.
"""

hex_data = "ffd8ffe000104a46494600010100000100010000ffdb00430001010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101ffc0000b080001002501011100ffc40017000100030000000000000000000000000006040708ffc400241000000209050100000000000000000000000702050608353776b6b7030436747577ffda0008010100003f00c54d3401dcbbfb9c38db8a7dd265a2159e9d945a086407383aabd52e5034c274e57179ef3bcdfca50f0af80aff00e986c64568c7ffd9"
jpeg_bytes = bytes.fromhex(hex_data)
image_file = io.BytesIO(jpeg_bytes)
image = Image.open(image_file)
pixel_values = list(image.getdata())
flag = "".join(chr(p) for p in pixel_values)
print(flag)