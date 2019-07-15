package com.yaozou.loadBalance;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/7/15 14:30
 */
public class EWMA {
    private static final long serialVersionUID = 2979391326784043002L;

    //时间类型枚举
    public static enum Time {
        MICROSECONDS(1),
        MILLISECONDS(1000),
        SECONDS(MILLISECONDS.getTime() * 1000),
        MINUTES(SECONDS.getTime() * 60),
        HOURS(MINUTES.getTime() * 60),
        DAYS(HOURS.getTime() * 24),
        WEEKS(DAYS.getTime() * 7);

        private long micros;

        private Time(long micros) {
            this.micros = micros;
        }

        public long getTime() {
            return this.micros;
        }
    }

    //三个alpha常量，这些值和Unix系统计算负载时使用的标准alpha值相同
    public static final double ONE_MINUTE_ALPHA = 1 - Math.exp(-5d / 60d / 1d);
    public static final double FIVE_MINUTE_ALPHA = 1 - Math.exp(-5d / 60d / 5d);
    public static final double FIFTEEN_MINUTE_ALPHA = 1 - Math.exp(-5d / 60d / 15d);
    private long window;
    private long alphaWindow;
    private long last;
    private double average;
    private double alpha = -1D;
    private boolean sliding = false;

    private long requests;//请求量
    private double weight;//权重

    public EWMA() {
    }

    public EWMA sliding(double count, Time time) {
        return this.sliding((long) (time.getTime() * count));
    }

    public EWMA sliding(long window) {
        this.sliding = true;
        this.window = window;
        return this;
    }

    public EWMA withAlpha(double alpha) {
        if (!(alpha > 0.0D && alpha <= 1.0D)) {
            throw new IllegalArgumentException("Alpha must be between 0.0 and 1.0");
        }
        this.alpha = alpha;
        return this;
    }

    public EWMA withAlphaWindow(long alphaWindow) {
        this.alpha = -1;
        this.alphaWindow = alphaWindow;
        return this;
    }

    public EWMA withAlphaWindow(double count, Time time) {
        return this.withAlphaWindow((long) (time.getTime() * count));
    }

    /**
     * 默认使用当前时间更新移动平均值
     */
    public void mark(){
        mark(System.currentTimeMillis());
    }

    /**
     * 更新移动平均值
     * @param time
     */
    public void mark(long time){
        if(this.sliding){
            //如果发生时间间隔大于窗口，则重置滑动窗口
            if(time-this.last > this.window){
                this.last = 0;
            }
        }
        if(this.last == 0){
            this.average = 0;
            this.requests = 0;
            this.last = time;
        }
        // 计算上一次和本次的时间差
        long diff = time - this.last;
        // 计算alpha
        double alpha = this.alpha != -1.0 ? this.alpha : Math.exp(-1.0*((double)diff/this.alphaWindow));
        // 计算当前平均值
        this.average = (1.0-alpha)*diff + alpha*this.average;
        this.last = time;
        // 请求量加1
        this.requests++;
        // 计算权重值
        this.weight = this.average != 0 ? this.requests/this.average : -1;
    }


    //返回mark()方法多次调用的平均值
    public double getAverage() {
        return this.average;
    }

    //按照特定的时间单位来返回平均值,单位详见Time枚举
    public double getAverageIn(Time time) {
        return this.average == 0.0 ? this.average : this.average / time.getTime();
    }

    //返回特定时间度量内调用mark()的频率
    public double getAverageRatePer(Time time) {
        return this.average == 0.0 ? this.average : time.getTime() / this.average;
    }

    //返回mark()方法多次调用的权重值
    public double getWeight() {return this.weight;}
    public long getRequests() {return this.requests;}

    public static void main(String[] args) {
        //建立1分钟滑动窗口EWMA实例
        EWMA ewma = new EWMA().
                sliding(1.0, Time.MINUTES).
                withAlpha(EWMA.ONE_MINUTE_ALPHA).
                withAlphaWindow(1.0, EWMA.Time.MINUTES);

        int randomSleep = 0;
        long markVal = System.currentTimeMillis() * 1000;//单位为微秒

        try {
            for (int i = 1; i <= 100000; i++) {
                randomSleep = ThreadLocalRandom.current().nextInt(50,200);
                markVal += randomSleep;
                ewma.mark(markVal);
                if (i % 1000 == 0) {
                    System.out.println("Round: " + i + ", Time: " + randomSleep
                            + ", Requests: " + ewma.getRequests()
                            + ", Average: " + ewma.getAverage()
                            + ", Weight:" + ewma.getWeight());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}