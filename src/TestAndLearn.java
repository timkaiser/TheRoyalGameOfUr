import java.util.Date;

public class TestAndLearn{


    //== Prince of Ur =================================================================================================//

    /* starting at 0 weights and binary searching the perfect weights
    * result 1: {702,1916,446,1598,-1766,1972,1022,762,414,1494,2022,92,204,0,0}
    * result 2: {1564,1808,1948,424,172,1182,1058,1396,0,1798,0,12,-2,0,0}
     */
    public static void test1 (){
        //initialize testing weights with 0s
        int weights[] = new int[15];

        //iterate step size
        steps:
        for (int i=1024; i!=1 ; i/=2) {
            //measurement for processing time
            long start = new Date().getTime();

            //iterate attributes
            attributes:
            for (int j = 0; j < weights.length-2/*ignore lest two attribute*/; j++) {
                //make copy of current weights
                int wTmp = weights[j];

                //initialize AI with unchanged weights
                PrinceOfUrAI normal = new PrinceOfUrAI(0);
                normal.setWeights(weights);

                //initialize AI with lowered weights
                weights[j] = wTmp - i;
                PrinceOfUrAI minus = new PrinceOfUrAI(1);
                minus.setWeights(weights);

                //test normal vs. minus
                for (int k = 0; k < 10000; k++) {
                    //reset tokens
                    normal.resetTokens();
                    minus.resetTokens();
                    //Compete
                    new Main(normal, minus, new Output(), 0);
                }

                //print results of first round
                System.out.println("==========================================");
                Main.printWincounter();
                System.out.println();

                //determine winner
                if(normal.getWincounter() >= minus.getWincounter()){
                    weights[j] = wTmp;
                    System.out.println(normal.getType() + " ("+normal.getWincounter()+" vs. "+minus.getWincounter()+")\n" );
                }else{
                    weights[j] = wTmp - i;
                    System.out.println(minus.getType() + " ("+minus.getWincounter()+" vs. "+normal.getWincounter()+")\n" );
                }

                //make new instance of winning AI (to reset wincounter)
                PrinceOfUrAI winner = new PrinceOfUrAI(0);
                winner.setWeights(weights);


                //reset wincounter
                Main.resetWincounter();

                //create AI with highered weights
                weights[j] = wTmp + i;
                PrinceOfUrAI plus = new PrinceOfUrAI(1);
                plus.setWeights(weights);

                //test winner vs. plus
                for (int k = 0; k < 10000; k++) {
                    //reset tokens
                    winner.resetTokens();
                    plus.resetTokens();

                    //compete
                    new Main(winner, plus, new Output(), 0);
                }

                //print results of second round
                Main.printWincounter();
                System.out.println("______________________________________");

                //determine final winner
                if(winner.getWincounter() >= plus.getWincounter()){
                    weights[j] = wTmp;
                    System.out.println(winner.getType() + " ("+winner.getWincounter()+" vs. "+plus.getWincounter()+")" );
                }else{
                    weights[j] = wTmp + i;
                    System.out.println(plus.getType() + " ("+plus.getWincounter()+" vs. "+winner.getWincounter()+")" );
                }

                //reset wincounter
                Main.resetWincounter();
            }

            //Print time and winner of this round
            System.out.println("Time for this step: "+(new Date().getTime()-start)+" Milliseconds ##########################################################################");
        }
    }

    // starting with results of test1 and binary searching the perfect weight. result: no improvement
    //result 1: {2326,2220,1910,3574,-502,4012,2038,882,1934,3078,8,1612,380,0,0}
    //result 2: {1800,1898,2022,520,410,1198,1066,1404,0,1822,0,256,-2,0,0}
    public static void test2(){
        //initialize testing weights with 0s
        int weights[] = {1564,1808,1948,424,172,1182,1058,1396,0,1798,0,12,-2,0,0};

        //iterate step size
        steps:
        for (int i=128; i!=1 ; i/=2) {
            //measurement for processing time
            long start = new Date().getTime();

            //iterate attributes
            attributes:
            for (int j = 0; j < weights.length-2/*ignore lest two attribute*/; j++) {
                //make copy of current weights
                int wTmp = weights[j];

                //initialize AI with unchanged weights
                PrinceOfUrAI normal = new PrinceOfUrAI(0);
                normal.setWeights(weights);

                //initialize AI with lowered weights
                weights[j] = wTmp - i;
                PrinceOfUrAI minus = new PrinceOfUrAI(1);
                minus.setWeights(weights);

                //test normal vs. minus
                for (int k = 0; k < 50000; k++) {
                    //reset tokens
                    normal.resetTokens();
                    minus.resetTokens();
                    //Compete
                    new Main(normal, minus, new Output(), 0);
                }

                //print results of first round
                System.out.println("==========================================");
                Main.printWincounter();
                System.out.println();

                //determine winner
                if(normal.getWincounter() >= minus.getWincounter()){
                    weights[j] = wTmp;
                    System.out.println(normal.getType() + " ("+normal.getWincounter()+" vs. "+minus.getWincounter()+")\n" );
                }else{
                    weights[j] = wTmp - i;
                    System.out.println(minus.getType() + " ("+minus.getWincounter()+" vs. "+normal.getWincounter()+")\n" );
                }

                //make new instance of winning AI (to reset wincounter)
                PrinceOfUrAI winner = new PrinceOfUrAI(0);
                winner.setWeights(weights);


                //reset wincounter
                Main.resetWincounter();

                //create AI with highered weights
                weights[j] = wTmp + i;
                PrinceOfUrAI plus = new PrinceOfUrAI(1);
                plus.setWeights(weights);

                //test winner vs. plus
                for (int k = 0; k < 50000; k++) {
                    //reset tokens
                    winner.resetTokens();
                    plus.resetTokens();

                    //compete
                    new Main(winner, plus, new Output(), 0);
                }

                //print results of second round
                Main.printWincounter();
                System.out.println("______________________________________");

                //determine final winner
                if(winner.getWincounter() >= plus.getWincounter()){
                    weights[j] = wTmp;
                    System.out.println(winner.getType() + " ("+winner.getWincounter()+" vs. "+plus.getWincounter()+")" );
                }else{
                    weights[j] = wTmp + i;
                    System.out.println(plus.getType() + " ("+plus.getWincounter()+" vs. "+winner.getWincounter()+")" );
                }

                //reset wincounter
                Main.resetWincounter();
            }

            //Print time and winner of this round
            System.out.println("Time for this step: "+(new Date().getTime()-start)+" Milliseconds ##########################################################################");
        }
    }

    /* Evolutionary algorithm
     * start: {1564,1808,1948,424,172,1182,1058,1396,0,1798,-1000,12,-2,0,0}
     * result 1: {1744,1358,1919,244,44,1408,532,1331,-306,1811,-1090,47,-369,0,0} [Improvement: 4.3%]
     * result 2: {1756,1610,1842,-145,143,966,602,803,-469,2114,-859,-129,-628,0,0} [Improvement: 10%]
     * result 3: {1291,1094,1844,-661,419,-33,137,554,-467,2847,-870,142,111,0,0} [Improvement: 10%, Total: 22%]
     * result 4: {1871,1118,1806,85,1008,-498,465,792,-699,3308,-1210,309,407,0,0} [Improvement: -0.5%]
     */

    public static void test4(){
        int weights[] = {1291,1094,1844,-661,419,-33,137,554,-467,2847,-870,142,111,0,0};

        PrinceOfUrAI[] princes = new PrinceOfUrAI[5];
        for (int i = 0; i < 5; i++) {

            //create princes
            princes[0] = new PrinceOfUrAI(0);
            princes[0].setWeights(weights);
            for (int j = 1; j < princes.length; j++) {

                //create random weights
                int newWeights[] = weights.clone();
                for (int k = 0; k < newWeights.length-2; k++) {
                    newWeights[k] += (int)(Math.random()*512 - 256);
                }
                princes[j] = new PrinceOfUrAI(0);
                princes[j].setWeights(newWeights);
            }

            //it's time to du du duellllll
            for (int j = 0; j < princes.length; j++) {
                for (int k = 0; k < princes.length; k++) {
                    if (j != k) {
                        for (int l = 0; l < 5000; l++) {
                            princes[j].setID(0);
                            princes[j].resetTokens();
                            princes[k].setID(1);
                            princes[k].resetTokens();
                            new Main(princes[j],princes[k],new Output(),0);
                        }
                        System.out.println(i+": ("+j+","+k+")");
                    }
                }
            }

            //find winner
            PrinceOfUrAI winner =  princes[0];
            for (int j = 1; j < princes.length; j++) {
                if(Main.getWins(winner.getType()) < Main.getWins(princes[j].getType())){
                    winner = princes[j];
                }
            }

            //set new weights
            weights = winner.getWeights();
            Main.printWincounter();
            Main.resetWincounter();
            System.out.println("_____________________________");
            System.out.println(winner.getType());

        }

    }

    //== Princess of Ur ===============================================================================================//
    /* starting at 0 weights and binary searching the perfect weights
     * result 1:
     * result 2:
     */
    public static void test3 (){
        long start = new Date().getTime();

        //initialize testing weights with 0s
        int weights[][] = new int[15][15];

        //iterate step size
        steps:
        for (int i=1024; i!=0 ; i/=2) {
            //measurement for processing time
            long startRound = new Date().getTime();

            //iterate attributes
            attributes:
            for (int j = 0; j < weights.length-2/*ignore lest two attribute*/; j++) {
                for (int l = 0; l < weights.length - 2/*ignore lest two attribute*/; l++) {
                    //make copy of current weights
                    int wTmp = weights[j][l];

                    //initialize AI with unchanged weights
                    PrincessOfUrAI normal = new PrincessOfUrAI(0);
                    normal.setWeights(weights);

                    //initialize AI with lowered weights
                    weights[j][l] = wTmp - i;
                    PrincessOfUrAI minus = new PrincessOfUrAI(1);
                    minus.setWeights(weights);

                    //test normal vs. minus
                    for (int k = 0; k < 10000; k++) {
                        //reset tokens
                        normal.resetTokens();
                        minus.resetTokens();
                        //Compete
                        new Main(normal, minus, new Output(), 0);
                    }

                    //print results of first round
                    //System.out.println("==========================================");
                    //Main.printWincounter();
                    //System.out.println();

                    //determine winner
                    if (normal.getWincounter() >= minus.getWincounter()) {
                        weights[j][l] = wTmp;
                        System.out.println(normal.getType() + " (" + normal.getWincounter() + " vs. " + minus.getWincounter() + ")\n");
                    } else {
                        weights[j][l] = wTmp - i;
                        System.out.println(minus.getType() + " (" + minus.getWincounter() + " vs. " + normal.getWincounter() + ")\n");
                    }

                    //make new instance of winning AI (to reset wincounter)
                    PrincessOfUrAI winner = new PrincessOfUrAI(0);
                    winner.setWeights(weights);


                    //reset wincounter
                    Main.resetWincounter();

                    //create AI with highered weights
                    weights[j][l] = wTmp + i;
                    PrincessOfUrAI plus = new PrincessOfUrAI(1);
                    plus.setWeights(weights);

                    //test winner vs. plus
                    for (int k = 0; k < 10000; k++) {
                        //reset tokens
                        winner.resetTokens();
                        plus.resetTokens();

                        //compete
                        new Main(winner, plus, new Output(), 0);
                    }

                    //print results of second round
                    //Main.printWincounter();
                    System.out.println("______________________________________");

                    //determine final winner
                    if (winner.getWincounter() >= plus.getWincounter()) {
                        weights[j][l] = wTmp;
                        System.out.println(winner.getType() + " (" + winner.getWincounter() + " vs. " + plus.getWincounter() + ")");
                    } else {
                        weights[j][l] = wTmp + i;
                        System.out.println(plus.getType() + " (" + plus.getWincounter() + " vs. " + winner.getWincounter() + ")");
                    }

                    //reset wincounter
                    Main.resetWincounter();
                }

                //Print time and winner of this round
                System.out.println("Time for this step: " + (new Date().getTime() - startRound) + " Milliseconds ##########################################################################");
            }
        }
        System.out.println("Time for this step: " + (new Date().getTime() - start) + " Milliseconds ##########################################################################");
    }



    //== other ========================================================================================================//

    public static void turnament(int[] w1, int[] w2){
        long start = new Date().getTime();
        for (int i = 0; i < 10000; i++) {

            PrinceOfUrAI p0 = new PrinceOfUrAI(0);
            //int[] weights0 = {1291,1094,1844,-661,419,-33,137,554,-467,2847,-870,142,111,0,0};
            p0.setWeights(w1);

            PrinceOfUrAI p1 = new PrinceOfUrAI(1);
            //int[] weights1 = {1756,1610,1842,-145,143,966,602,803,-469,2114,-859,-129,-628,0,0};
            p1.setWeights(w2);

            new Main(p0 , p1, new Output(), 0);
        }
        long time = new Date().getTime()-start;
        Main.printWincounter();
        System.out.println("Time: "+time);
        Main.printStatistics();
    }


    public static void main(String args[]){
        int[] w1={1291,1094,1844,-661,419,-33,137,554,-467,2847,-870,142,111,0,0};
        int[] w2={1717,1144,1445,-601,542,-323,428,557,-441,2756,-1343,454,311,0,0};
        turnament(w1,w2);

        //test4();
        /*for (int i = 0; i < 100000; i++) {
            new Main(new KingOfUrAI(0) , new RandomAI(1), new Output(), 0);
        }

        Main.printWincounter();*/

    }

    /* best result:
    * {1564,1808,1948,424,172,1182,1058,1396,0,1798,-1000,12,-2,0,0}
    */
}
