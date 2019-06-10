//  ZDT1.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//




//




// 



package org.uma.jmetal.problem.lj;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Class representing problem ZDT1 */
@SuppressWarnings("serial")
public class QoS extends AbstractDoubleProblem {


  List<Double[]> list = new ArrayList<>();




  /** Constructor. Creates default instance of problem ZDT1 (30 decision variables) */
  public QoS() {
    this(5);

      String pathname = "test.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
      //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
      //不关闭文件会导致资源的泄露，读写文件都同理
      //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
      try (FileReader reader = new FileReader(pathname);
           BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
      ) {
        String line;
        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) {
          // 一次读入一行数据
          String[] s = line.split(" ");
          Double[] d = new Double[5];
          for (int i = 0; i < s.length; i++) {
            d[i]=Double.parseDouble(s[i]);
          }
          list.add(d);

        }
      } catch (IOException e) {
        e.printStackTrace();
      }

  }

  /**
   * Creates a new instance of problem ZDT1.
   *
   * @param numberOfVariables Number of variables.
   */
  public QoS(Integer numberOfVariables) {
    setNumberOfVariables(numberOfVariables);
    setNumberOfObjectives(2);
    setName("QoS");

    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(0.0);
      upperLimit.add(100.0);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);
  }

  /** Evaluate() method */
  public void evaluate(DoubleSolution solution) {
    double[] f = new double[getNumberOfObjectives()];
    int v0 = (int)Math.ceil(solution.getVariableValue(0));
    int v1 = (int)Math.ceil(solution.getVariableValue(1))+10000*1;
    int v2 = (int)Math.ceil(solution.getVariableValue(2))+10000*2;
    int v3 = (int)Math.ceil(solution.getVariableValue(3))+10000*3;
    int v4 = (int)Math.ceil(solution.getVariableValue(4))+10000*4;
    Double[] d1 = list.get(v0);
    Double[] d2 = list.get(v1);
    Double[] d3 = list.get(v2);
    Double[] d4 = list.get(v3);
    Double[] d5 = list.get(v4);
    f[0] =(d1[0]+d2[0]+d3[0]+d4[0]+d5[0]+d1[2]+d2[2]+d3[2]+d4[2]+d5[2])/10;

//    f[1] = (d1[1]+d2[1]+d3[1]+d4[1]+d5[3]+d1[3]+d2[3]+d3[3]+d4[3]+d5[3]+d1[4]+d2[4]+d3[4]+d4[4]+d5[4])/15;

    solution.setObjective(0, f[0]);
//    solution.setObjective(1, f[1]);
  }

  /**
   * Returns the value of the ZDT1 function G.
   *
   * @param solution Solution
   */
  private double evalG(DoubleSolution solution) {
    double g = 0.0;
    for (int i = 1; i < solution.getNumberOfVariables(); i++) {
      g += solution.getVariableValue(i);
    }
    double constant = 9.0 / (solution.getNumberOfVariables() - 1);
    g = constant * g;
    g = g + 1.0;
    return g;
  }

  /**
   * Returns the value of the ZDT1 function H.
   *
   * @param f First argument of the function H.
   * @param g Second argument of the function H.
   */
  public double evalH(double f, double g) {
    double h ;
    h = 1.0 - Math.sqrt(f / g);
    return h;
  }

  public static void main(String[] args) throws IOException {
    File file =new File("test.txt");
    FileWriter fileWriter =new FileWriter("text.txt");
    try {
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);

      for(int i = 0; i<50000;i++){
        Random random = new Random();
        bw.write(random.nextDouble()+" "+random.nextDouble()+" "+random.nextDouble()+" "+random.nextDouble()+" "+random.nextDouble()+"\r\n");
        bw.flush();
      }
      bw.close();
      fw.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
