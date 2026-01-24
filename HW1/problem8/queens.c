#include <pthread.h>
#include <stdio.h>
#include "board.h"
#include "safe.h"

#define N 8

int count = 0;
void solve(int col, int **board){

    if(col == N){
        count++;
        printf("count: %i \n", count);
        printboard(board);
        return;
    }

    for(int row = 0; row < N; row++){
        if (safe(row, col, board)){
            board[row][col] = 1;

            solve(col+1, board);
            
            board[row][col] = 0;
        }
    }

}

int main(){
    int **b = initboard();
    solve(0, b);
    printf("%i", count);
}