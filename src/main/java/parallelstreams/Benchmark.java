package parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 */
public class Benchmark {
  public static void main(String[] args) throws Exception {
    Benchmarker bm = new Benchmarker(100000);
    for (int j = 0; j < 10; j++) {
      List<String> list = new ArrayList<>();
      for (int i = 0; i < 100 * Math.pow(2, j); i++) {
        list.add("foorbar" + i);
      }
      System.out.print("Elements: " + list.size());
      bm.execute("for loop", () -> {
        int i = 0;
        for (String s : list) {
          if (s.endsWith("1")) {
            i++;
          }
        }
      });
      System.out.print(".");
      bm.execute("sequential stream", () -> {
        list.stream().filter(v -> v.endsWith("1")).count();
      });
      System.out.print(".");
      bm.execute("parallel stream", () -> {
        list.stream().parallel().filter(v -> v.endsWith("1")).count();
      });
      System.out.println(".");
      bm.report();
    }
  }
}
