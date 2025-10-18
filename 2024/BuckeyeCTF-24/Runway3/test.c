#include <stdio.h>

int main(void) {
    char input[50]; // intialize 50 spaces of memory

    gets(input); // input from the user, make sure to enter less than 50 characters

    printf("This is what you entered: '%s'\n", input); // print back the input to the user!

    return 0;
}