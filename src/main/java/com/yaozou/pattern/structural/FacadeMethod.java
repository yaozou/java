package com.yaozou.pattern.structural;/**
 * created by yaozou on 2018/4/20
 */

/**
 * 外观模式
 * @author yaozou
 * @create 2018-04-20 16:12
 **/
public class FacadeMethod {
    public static void main(String[] args) {
        /**
         *  为了解决类与类之家的依赖关系的，像spring一样，可以将类和类之间的关系配置到配置文件中，
         *  而外观模式就是将他们的关系放在一个Facade类中，降低了类类之间的耦合度，该模式中没有涉及到接口
         */
        // 例如打开电脑
        FacadeMethod method = new FacadeMethod();
        method.open();
        method.close();
    }

    class CPUClass{
       public void startup(){
            System.out.println("CPU startup......");
       }
       public void stopdown(){
           System.out.println("CPU stopdown......");
       }
    }

    class MemoryClass{
        public void startup(){
            System.out.println("Memory startup......");
        }
        public void stopdown(){
            System.out.println("Memory stopdown......");
        }
    }

    class DiskClass{
        public void startup(){
            System.out.println("Disk startup......");
        }
        public void stopdown(){
            System.out.println("Disk stopdown......");
        }
    }

    class Computer{
        private CPUClass cpu;
        private MemoryClass memory;
        private DiskClass disk;

        public Computer(){
            this.cpu = new CPUClass();
            this.memory = new MemoryClass();
            this.disk = new DiskClass();
        }

        public void startup(){
            cpu.startup();
            memory.startup();
            disk.startup();
        }

        public void stopdown(){
            cpu.stopdown();
            memory.stopdown();
            disk.stopdown();
        }
    }

    public void open(){
        Computer computer = new Computer();
        computer.startup();
    }

    public void close(){
        Computer computer = new Computer();
        computer.stopdown();
    }
}
