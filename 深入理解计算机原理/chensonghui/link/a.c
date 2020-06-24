extern int shared;
int b=10;
int main(){
	int a=100;
	a = b;
	swap(&a, &shared);
}