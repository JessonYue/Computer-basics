#include <stdio.h>
void ReadElfHeader(FILE * file);
int main() {
    FILE * file = fopen("libnative-lib.so","rb");
    ReadElfHeader(file);
    return 0;
}
