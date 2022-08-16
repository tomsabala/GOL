public class Main {
    public static void main(String[] args){
        GameSet game = new GameSet(4, 4, new char[][]{{'*', '.', '.', '*'}, {'.', '*', '*', '.'},
                {'*', '.', '.', '*'}, {'.', '*', '*', '.'}});

        while(game.isLife() && game.getStep() < 10){
            System.out.println(game.toString());
            System.out.println("------------------");
            game.generation();
        }

        System.out.println(game.getStep());
    }
}
