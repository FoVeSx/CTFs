# Conversion map
hex_to_char_map = {
    "04": "a", "05": "b", "06": "c", "07": "d", "08": "e", "09": "f", "0A": "g", "0B": "h",
    "0C": "i", "0D": "j", "0E": "k", "0F": "l", "10": "m", "11": "n", "12": "o", "13": "p",
    "14": "q", "15": "r", "16": "s", "17": "t", "18": "u", "19": "v", "1A": "w", "1B": "x",
    "1C": "y", "1D": "z", "1E": "1", "1F": "2", "20": "3", "21": "4", "22": "5", "23": "6",
    "24": "7", "25": "8", "26": "9", "27": "0", "2C": " ", "28": "\n", "2D": "-", "34": "'",
    "36": ",", "2E": "=", "33": ";", "2F": "{", "30": "}"
}

def convert_bytes_to_char(file_path):
    # Initialize an empty string to hold the final result
    final_string = ""
    
    # Open and read the file
    with open(file_path, 'r') as file:
        data_list = file.readlines()
    
    # Process each line from the file
    for data in data_list:
        data = data.strip()  # Remove any trailing newline or spaces
        
        # Extract the 3rd and 4th bytes (6th to 9th character in hex string)
        byte_3 = data[4:6]
        byte_4 = data[6:8]
        print(byte_4)
        #byte_3_4 = data[4:8]
        byte_3 = byte_3.upper()
        byte_4 = byte_4.upper()

        # Get the corresponding character from the map, skip if not found
        converted_char1 = hex_to_char_map.get(byte_3)
        converted_char2 = hex_to_char_map.get(byte_4)

        if converted_char1:
            # Append the converted character to the final string if found
            final_string += converted_char1
        
        if converted_char2:
            # Append the converted character to the final string if found
            final_string += converted_char2
    
    # Print the final string after processing all data
    print(final_string)

# Example usage
file_path = 'data_file.txt'  # Replace with the path to your file
convert_bytes_to_char(file_path)
