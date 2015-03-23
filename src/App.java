import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import edu.rice.hj.api.SuspendableException;

import java.util.Random;
import java.util.Scanner;

import static edu.rice.hj.Module1.async;
import static edu.rice.hj.Module1.finish;


public class App{
    public static int sum = 0, sum1, sum2;

    public static void main(String[] args) throws SuspendableException {
        long startingTime, elapsedTime;
        Scanner input = new Scanner(System.in);
        int[] sampleArray;
        int size;
        Random filling;

        /// Enter the array size ///
        System.out.print("Enter your array size: ");
        size = input.nextInt();

        sampleArray = new int[size];
        filling = new Random(size);

        /// Starting to fill the array ///
        for (int i = 0; i < size; i++) {
            sampleArray[i] = filling.nextInt();
        }

        startingTime = System.nanoTime();

        finish(()->{
            async(()-> {
                for (int i = 0; i < size / 2; i++) {
                    sum1 += sampleArray[i];
                }
            });
            async(()-> {
                for (int i = size / 2; i < size; i++) {
                    sum2 += sampleArray[i];
                }
            });
        });

        sum = sum1 + sum2;

        elapsedTime = System.nanoTime() - startingTime;
        System.out.println("Parallel time: " + elapsedTime/6 + " Millisecond");

        sum1 = sum2 = 0;
        startingTime = System.nanoTime();

        for (int i = 0; i < size / 2; i++) {
            sum1 += sampleArray[i];
        }
        for (int i = size / 2; i < size; i++) {
            sum2 += sampleArray[i];
        }

        elapsedTime = System.nanoTime() - startingTime;
        sum = sum1 + sum2;
        System.out.println("Sequential time: " + elapsedTime / 6 + " Millisecond");


    }

}