import java.util.Arrays;

public class GameSet {
    private final int n_grid;
    private final int m_grid;
    private final char[][] init_grid;
    private char[][] grid;
    private long step;
    private boolean life;

    public GameSet(int n, int m){
        this(n, m, new char[n][m]);
        for(int i=0; i<n; ++i){
            for(int j=0; j<m; ++j){
                this.grid[i][j] = '.';
            }
        }
    }
    public GameSet(int n, int m, char[][] grid){
        this.n_grid = n;
        this.m_grid = m;
        this.grid = grid;
        this.init_grid = new char[n][m];
        this.step = 0;
        this.life = true;
        for(int i=0; i<n; ++i){
            for(int j=0; j<m; ++j){
                this.init_grid[i][j] = '.';
            }
        }
    }

    public void generation() {
        char[][] next_gen = new char[this.n_grid][this.m_grid];
        int pop = 0;
        long changes = 0;
        for(int i=0; i<this.n_grid; ++i) {
            for(int j=0; j<this.m_grid; ++j) {
                pop = eval(i, j);
                if(grid[i][j] == '*'){
                    if (pop == 2 || pop == 3) {
                        next_gen[i][j] = '*';
                    }  else {
                        next_gen[i][j] = '.';
                        changes ++;
                    }
                }
                if(grid[i][j] == '.'){
                    if (pop == 3){
                        next_gen[i][j] = '*';
                        changes ++;
                    } else {
                        next_gen[i][j] = '.';
                    }
                }
            }
        }
        if(changes == 0){
            life = false;
        }
        grid = next_gen;
        step ++;
    }
    private int eval(int i, int j) {
        int population = 0;
        if(i>0){
            if (j<this.m_grid-1)
                population += grid[i-1][j+1] == '*' ? 1 : 0;
            population += grid[i-1][j] == '*' ? 1 : 0;
            if(j>0)
                population += grid[i-1][j-1] == '*' ? 1 : 0;
        }
        if(j>0)
            population += grid[i][j-1] == '*' ? 1 : 0;
        if(j<this.m_grid-1)
            population += grid[i][j+1] == '*' ? 1 : 0;
        if(i<this.n_grid-1){
            if (j<this.m_grid-1)
                population += grid[i+1][j+1] == '*' ? 1 : 0;
            population += grid[i+1][j] == '*' ? 1 : 0;
            if(j>0)
                population += grid[i+1][j-1] == '*' ? 1 : 0;
        }
        return population;
    }

    public void reset() {
        this.grid = this.init_grid;
        this.life = true;
        this.step = 0;
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        for(int i=0; i<this.n_grid; ++i){
            res.append(Arrays.toString(this.grid[i])).append('\n');
        }
        return res.toString();
    }

    public int getN_grid() {
        return n_grid;
    }

    public int getM_grid() {
        return m_grid;
    }

    public char[][] getInit_grid() {
        return init_grid;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(int i, int j, char c) {
        this.grid[i][j] = c;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }
}
