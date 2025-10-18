def main():
    encrypted_flag = [62,63,40,58,39,40,111,63,52,50,53,63,104,48,48,37,3,61,3,55,57,37,48,108,59,59,111,46,33]
    answer = ""
    for x in encrypted_flag:
        answer += chr(x ^ 92)
    print(answer)

if __name__ == '__main__':
    main()