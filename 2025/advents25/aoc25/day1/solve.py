import os

def solve_safe_password(rotations):
    dial_size = 100
    current_position = 50
    zero_hits = 0

    print(f"Starting Position: {current_position}")
    print("-" * 30)

    for rotation in rotations:
        rotation = rotation.strip()
        if not rotation:
            continue

        direction = rotation[0]
        amount = int(rotation[1:])

        if direction == 'R':
            current_position = (current_position + amount) % dial_size
        elif direction == 'L':
            current_position = (current_position - amount) % dial_size

        if current_position == 0:
            zero_hits += 1

    return zero_hits

def main():
    input_filename = 'input.txt'
    
    if os.path.exists(input_filename):
        print(f"Reading from {input_filename}...")
        with open(input_filename, 'r') as f:
            rotations = f.readlines()
    else:
        print(f"'{input_filename}' not found. Running example data from prompt...")
        example_data = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
        """
        rotations = example_data.strip().split('\n')

    password = solve_safe_password(rotations)
    
    print("-" * 30)
    print(f"Final Password: {password}")

if __name__ == "__main__":
    main()
