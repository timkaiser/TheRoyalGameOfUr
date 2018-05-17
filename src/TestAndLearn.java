import java.util.Date;

public class TestAndLearn{

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

    public static void turnament(){
        long start = new Date().getTime();
        for (int i = 0; i < 100000; i++) {

            PrinceOfUrAI p0 = new PrinceOfUrAI(0);
            int[] weights0 = {1564,1808,1948,424,172,1182,1058,1396,0,1798,0,12,-2,0,0};
            p0.setWeights(weights0);

            PrinceOfUrAI p1 = new PrinceOfUrAI(1);
            int[] weights1 = {1800,1898,2022,520,410,1198,1066,1404,0,1822,0,256,-2,0,0};
            p1.setWeights(weights1);

            new Main(p0 ,new SimpleAI(1), new Output(), 0);
        }
        long time = new Date().getTime()-start;
        Main.printWincounter();
        System.out.println("Time: "+time);
        Main.printStatistics();
    }


    public static void main(String args[]){
        turnament();
    }

}
