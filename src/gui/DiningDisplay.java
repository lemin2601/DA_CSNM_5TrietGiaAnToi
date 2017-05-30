package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import dinningphilosopherproblem.Information;
import dinningphilosopherproblem.PhilosopherProblem;
import gfx.Assets;

/**
 *
 * @author Lê Minh Trung 13T4
 */
public class DiningDisplay extends javax.swing.JFrame implements Runnable{
    
	// dining chưa thông tin của các philo
 	DiningState state;
 	// Khai báo một Philo Dining
 	PhilosopherProblem PhilosopherProblem;
	 	
	
	//Các thông tin để sử dụng cho việc set Information
	private static int timeThinking = 3000;
    private static int timeWaiting = 500;
    private static int timeEating = 2000;
    private static int numberOfMethod = 0;
    private static int numberOfPhilosopher = 5;
    
 	//thread hiển thị và canvas Frame các loại
 	private Thread thread;
 	private boolean isrunning = false;
 	// canvas vẽ hoạt ảnh
 	private BufferStrategy bs;
 	private Graphics g;
 	
 	public void run() {
 		
 		init();

 		whileOfRun();
 			

 	}
 	// Loop
 	public void whileOfRun(){
 		int fps = 60;
 		double timePerTick = 1000000000 / fps;
 		double delta = 0;
 		long now;
 		long lastTime = System.nanoTime();
 		long timer = 0;
 		int ticks = 0;
 		
 		while(isrunning){
 			now = System.nanoTime();
 			delta += (now - lastTime) / timePerTick;
 			timer += now - lastTime;
 			lastTime = now;
 			
 			if(delta >= 1){
 				tick();
 				render();
 				ticks++;
 				delta--;
 			}
 			
 			if(timer >= 1000000000){
 				System.out.println("Ticks and Frames: " + ticks);
 				ticks = 0;
 				timer = 0;
 			}
 		}
 		

 	}
 	// khởi tạo ban đầu
 	private void init(){
 		Assets.init();
 		
 		PhilosopherProblem = (new PhilosopherProblem(Information.NUM_OF_PHILOSOPHER));
 		PhilosopherProblem.start();
 		state = new DiningState(PhilosopherProblem,Information.NUM_OF_PHILOSOPHER);
 		//state.setState(PhilosopherProblem);
 	}
 	// Reset State
 	private void tick(){
 		state.tick();
 		txtStatus.setText(state.getStatus());
 	}
 	// Draw Canvas
 	private void render(){
 	    bs = this.canvas.getBufferStrategy();
 	    if(bs == null){
 	        this.canvas.createBufferStrategy(3);
 	        return;
 	    }
 	    g = bs.getDrawGraphics();
 		//Clear Screen
 		g.clearRect(0, 0, this.canvas.getWidth(),this.canvas.getHeight());
 		//Draw Here!
 	    state.render(g);  
 	    //End Drawing
 	    bs.show();
 	    g.dispose();
 	}
 	// start chương trình.
 	public synchronized void start(){
 		if(isrunning) return;
 		isrunning = true;
 		thread = new Thread(this);
 		thread.start();		
 		this.setVisible(true);
 	}
 	// stop chương trình
 	public synchronized void stop(){
 		if(!isrunning) return;
 		isrunning  = false;
 		if(!isrunning){		   
 	 		thread.stop();
 	 		PhilosopherProblem.stop();
 		}
 		
 	} 
    //Constructor
    public DiningDisplay() {
        initComponents();
    }
    //GUI
     private void initComponents() {

        canvas = new java.awt.Canvas();
        panel1 = new java.awt.Panel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        sliderThinking = new javax.swing.JSlider();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnSet = new javax.swing.JButton();
        lblThinking = new javax.swing.JLabel();
        lblWaiting = new javax.swing.JLabel();
        sliderWaiting = new javax.swing.JSlider();
        lblEating = new javax.swing.JLabel();
        sliderEating = new javax.swing.JSlider();
        cBxNumOfPhilosopher = new javax.swing.JComboBox<>();
        cBxMethod = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnDetailMethod = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GVHD: Nguyễn Thế Xuân Ly");

        canvas.setBackground(new java.awt.Color(250, 250, 250,250));
        canvas.setMinimumSize(new java.awt.Dimension(652, 652));
        canvas.setName(""); // NOI18N

        panel1.setBackground(new java.awt.Color(204, 204, 204));

        txtStatus.setColumns(20);
        txtStatus.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtStatus.setRows(5);
        txtStatus.setText("Name       State   NumOfEat\nPhilo 1:   Think       0\nPhilo 2:   Eat         0\nPhilo 3:   Wait        0\nPhilo 4:   Think       0\nPhilo 5:   Think       0\nPhilo 6:   Think       0");
        jScrollPane1.setViewportView(txtStatus);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Number Of Philosopher:");

        sliderThinking.setMajorTickSpacing(5);
        sliderThinking.setMaximum(50);
        sliderThinking.setMinorTickSpacing(10);
        sliderThinking.setPaintLabels(true);
        sliderThinking.setValue(30);
        sliderThinking.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderThinkingStateChanged(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnSet.setText("set");
        btnSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetActionPerformed(evt);
            }
        });

        lblThinking.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblThinking.setText("Time for Thinking: 3000 ms");

        lblWaiting.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblWaiting.setText("Time for Waitting: 500 ms");

        sliderWaiting.setMajorTickSpacing(2);
        sliderWaiting.setMaximum(20);
        sliderWaiting.setPaintLabels(true);
        sliderWaiting.setValue(5);
        sliderWaiting.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderWaitingStateChanged(evt);
            }
        });

        lblEating.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblEating.setText("Time for Eating: 2000 ms");

        sliderEating.setMajorTickSpacing(5);
        sliderEating.setMaximum(50);
        sliderEating.setMinorTickSpacing(10);
        sliderEating.setPaintLabels(true);
        sliderEating.setValue(20);
        sliderEating.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderEatingStateChanged(evt);
            }
        });

        cBxNumOfPhilosopher.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cBxNumOfPhilosopher.setMaximumRowCount(5);
        cBxNumOfPhilosopher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5", "6" }));
        cBxNumOfPhilosopher.setSelectedIndex(3);
        cBxNumOfPhilosopher.setToolTipText("");
        cBxNumOfPhilosopher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBxNumOfPhilosopherActionPerformed(evt);
            }
        });

        cBxMethod.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cBxMethod.setMaximumRowCount(5);
        cBxMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A Simpler Solution", "Arbitrator solution", "Chandy/Misra solution", "Resource hierarchy solution", "Tannenbaum's Solution" }));
        cBxMethod.setToolTipText("");
        cBxMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBxMethodActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Method:");

        btnDetailMethod.setText("jButton1");
        btnDetailMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailMethodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sliderThinking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sliderWaiting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sliderEating, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblEating, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblWaiting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThinking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cBxNumOfPhilosopher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cBxMethod, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetailMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cBxMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetailMethod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cBxNumOfPhilosopher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThinking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderThinking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblWaiting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEating)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderEating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnStop)
                        .addComponent(btnSet))
                    .addComponent(btnStart))
                .addGap(18, 18, 18))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Đồ Án Cơ Sở Ngành Mạng - Hệ Điều Hành");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Phần Hệ Điều Hành               SVTH:  Lê Minh Trung-13T4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }
    //EVENT 
    private void btnSetActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	Information.timeEatingRandom = timeEating;
    	Information.timeThinkingRandom = timeThinking;
    	Information.timeWaitingRandom = timeWaiting;
    	Information.NUM_OF_PHILOSOPHER = numberOfPhilosopher;
    	Information.numOfMethod = numberOfMethod;
    	stop();
    }
    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {
    	stop();
    }
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
    	start();
    }
    private void sliderThinkingStateChanged(javax.swing.event.ChangeEvent evt) {
        JSlider source = (JSlider)evt.getSource();
        //if (!source.getValueIsAdjusting()) 
        {
            int time = (int)source.getValue();

            if (time != 0) {
                timeThinking = time*100; 
            }else{
                timeThinking =100;
            }
             this.lblThinking.setText("Time for Thinking: " + timeThinking +" ms");
        }
    }//GEN-LAST:event_sliderThinkingStateChanged

    private void sliderWaitingStateChanged(javax.swing.event.ChangeEvent evt) {
        JSlider source = (JSlider)evt.getSource();
        //if (!source.getValueIsAdjusting()) 
        {
            int time = (int)source.getValue();

            if (time != 0) {
                timeWaiting = time*100; 
            }else{
                timeWaiting =100;
            }
             this.lblWaiting.setText("Time for Waiting: " + timeWaiting +" ms");
        }
    }//GEN-LAST:event_sliderWaitingStateChanged

    private void sliderEatingStateChanged(javax.swing.event.ChangeEvent evt) {
        JSlider source = (JSlider)evt.getSource();
        //if (!source.getValueIsAdjusting()) 
        {
            int time = (int)source.getValue();

            if (time != 0) {
                timeEating = time*100; 
            }else{
                timeEating =100;
            }
             this.lblEating.setText("Time for Eating: " + timeEating +" ms");
        }
    }

    private void btnDetailMethodActionPerformed(java.awt.event.ActionEvent evt) {
        String method[] ={"A Simpler Solution",
                        "Arbitrator solution",
                        "Chandy/Misra solution",
                        "Resource hierarchy solution",
                        "Tannenbaum's Solution"};
        String detailMethod[] = {
					"\nA Simpler Solution. Other authors, including Dijkstra, have posed simpler solutions to the dining philosopher "
					+ "\nproblem than that proposed by Tannenbaum (depending on one's notion of \"simplicity,\" of course). One such solution"
					+ "\n is to restrict the number of philosophers allowed access to the table. If there are N chopsticks but only N-1 "
					+ "\nphilosophers allowed to compete for them, at least one will succeed, even if they follow a rigid sequential protocol"
					+ "\n to acquire their chopsticks.\n" +
					"\n" +
					"\nThis solution is implemented with an integer semaphore, initialized to N-1. Both this and Tannenbaum's solutions "
					+ "\navoid deadlock a situation in which all of the philosophers have grabbed one chopstick and are deterministically"
					+ "\n waiting for the other, so that there is no hope of recovery. However, they may still permit starvation, a scenario"
					+ "\n in which at least one hungry philosopher never gets to eat.\n" +
					"\n" +
					"\nStarvation occurs when the asynchronous semantics may allow an individual to eat repeatedly, thus keeping another from"
					+ "\n getting a chopstick. The starving philosopher runs, perhaps, but doesn't make progress. The observation of this fact"
					+ "\n leads to some further refinement of what fairness means. Under some notions of fairness the solutions given above can"
					+ "\n be said to be correct."
					,"\nAnother approach is to guarantee that a philosopher can only pick up both forks or none by introducing an arbitrator, e.g.,"
					+ "\n a waiter. In order to pick up the forks, a philosopher must ask permission of the waiter. The waiter gives "
					+ "\npermission to only one philosopher at a time until the philosopher has picked up both of their forks. Putting "
					+ "\ndown a fork is always allowed. The waiter can be implemented as a mutex. In addition to introducing a new central"
					+ "\n entity (the waiter), this approach can result in reduced parallelism: if a philosopher is eating and one of their"
					+ "\n neighbors is requesting the forks, all other philosophers must wait until this request has been fulfilled even if"
					+ "\n forks for them are still available."
					,"\nIn 1984, K. Mani Chandy and J. Misra[5] proposed a different solution to the dining philosophers problem to allow for"
					+ "\n arbitrary agents (numbered P1, ..., Pn) to contend for an arbitrary number of resources, unlike Dijkstra's solution."
					+ "\n It is also completely distributed and requires no central authority after initialization. However, it violates the"
					+ "\n requirement that \"the philosophers do not speak to each other\" (due to the request messages).\n" +
					"\nFor every pair of philosophers contending for a resource, create a fork and give it to the philosopher with the lower "
					+ "\nID (n for agent Pn). Each fork can either be dirty or clean. Initially, all forks are dirty.\n" +
					"\nWhen a philosopher wants to use a set of resources (i.e. eat), said philosopher must obtain the forks from their contending"
					+ "\n neighbors. For all such forks the philosopher does not have, they send a request message.\n" +
					"\nWhen a philosopher with a fork receives a request message, they keep the fork if it is clean, but give it up when it is dirty."
					+ "\n If the philosopher sends the fork over, they clean the fork before doing so.\n" +
					"\nAfter a philosopher is done eating, all their forks become dirty. If another philosopher had previously requested one of the forks,"
					+ "\n the philosopher that has just finished eating cleans the fork and sends it.\n" +
					"\nThis solution also allows for a large degree of concurrency, and will solve an arbitrarily large problem.\n" +
					"\nIt also solves the starvation problem. The clean / dirty labels act as a way of giving preference to the most \"starved\" processes,"
					+ "\n and a disadvantage to processes that have just \"eaten\". One could compare their solution to one where philosophers are not allowed"
					+ "\n to eat twice in a row without letting others use the forks in between. Chandy and Misra's solution is more flexible than that, but has"
					+ "\n an element tending in that direction.\n" +
					"\nIn their analysis they derive a system of preference levels from the distribution of the forks and their clean/dirty states. They show "
					+ "\nthat this system may describe an acyclic graph, and if so, the operations in their protocol cannot turn that graph into a cyclic one."
					+ "\nThis guarantees that deadlock cannot occur. However, if the system is initialized to a perfectly symmetric state, like all philosophers "
					+ "\nholding their left side forks, then the graph is cyclic at the outset, and their solution cannot prevent a deadlock. Initializing "
					+ "\nthe system so that philosophers with lower IDs have dirty forks ensures the graph is initially acyclic."
					, "\nThis solution to the problem is the one originally proposed by Dijkstra. It assigns a partial order to the resources "
            		+ "\n(the forks, in this case), and establishes the convention that all resources will be requested in order, and that"
            		+ "\n no two resources unrelated by order will ever be used by a single unit of work at the same time. Here, the "
            		+ "\nresources (forks) will be numbered 1 through 5 and each unit of work (philosopher) will always pick up the "
            		+ "\nlower-numbered fork first, and then the higher-numbered fork, from among the two forks they plan to use. The order"
            		+ "\n in which each philosopher puts down the forks does not matter. In this case, if four of the five philosophers "
            		+ "\nsimultaneously pick up their lower-numbered fork, only the highest-numbered fork will remain on the table, so the "
            		+ "\nfifth philosopher will not be able to pick up any fork. Moreover, only one philosopher will have access to that "
            		+ "\nhighest-numbered fork, so they will be able to eat using two forks.\n" +
					"\nWhile the resource hierarchy solution avoids deadlocks, it is not always practical, especially when the"
					+ "\n list of required resources is not completely known in advance. For example, if a unit of work holds "
					+ "\nresources 3 and 5 and then determines it needs resource 2, it must release 5, then 3 before acquiring 2,"
					+ "\n and then it must re-acquire 3 and 5 in that order. Computer programs that access large numbers of database"
					+ "\n records would not run efficiently if they were required to release all higher-numbered records before"
					+ "\n accessing a new record, making the method impractical for that purpose.[2]"
					,"\nTannenbaum's Solution. This solution uses only boolean semaphors. There is one global semaphore to provide mutual exclusion"
            		+ "\n for exectution of critical protocols. There is one semaphore for each chopstick. In addition, a local two-phase"
            		+ "\n prioritization scheme is used, under which philosophers defer to their neighbors who have declared themselves "
            		+ "\n\"hungry.\" All arithmetic is modulo 5.\n" +
					"system DINING_PHILOSOPHERS\n" +
					"VAR\n" +
					"me:    semaphore, initially 1;                    /* for mutual exclusion */\n" +
					"s[5]:  semaphore s[5], initially 0;               /* for synchronization */\n" +
					"pflag[5]: {THINK, HUNGRY, EAT}, initially THINK;  /* philosopher flag */\n" +
					"As before, each philosopher is an endless cycle of thinking and eating.\n" +
					"procedure philosopher(i)\n" +
					"  {\n" +
					"    while TRUE do\n" +
					"     {\n" +
					"       THINKING;\n" +
					"       take_chopsticks(i);\n" +
					"       EATING;\n" +
					"       drop_chopsticks(i);\n" +
					"     }\n" +
					"  }\n" +
					"The take_chopsticks procedure involves checking the status of neighboring philosophers and then declaring one's own intention to eat."
					+ "\n This is a two-phase protocol; first declaring the status HUNGRY, then going on to EAT.\n" +
					"procedure take_chopsticks(i)\n" +
					"  {\n" +
					"    DOWN(me);               /* critical section */\n" +
					"    pflag[i] := HUNGRY;\n" +
					"    test[i];\n" +
					"    UP(me);                 /* end critical section */\n" +
					"    DOWN(s[i])              /* Eat if enabled */\n" +
					"   }\n" +
					"void test(i)            /* Let phil[i] eat, if waiting */\n" +
					"  {\n" +
					"    if ( pflag[i] == HUNGRY\n" +
					"      && pflag[i-1] != EAT\n" +
					"      && pflag[i+1] != EAT)\n" +
					"       then\n" +
					"        {\n" +
					"          pflag[i] := EAT;\n" +
					"          UP(s[i])\n" +
					"         }\n" +
					"    }\n" +
					"Once a philosopher finishes eating, all that remains is to relinquish the resources---its two chopsticks---and thereby "
					+ "\nrelease waiting neighbors.\n" +
					"void drop_chopsticks(int i)\n" +
					"  {\n" +
					"    DOWN(me);                /* critical section */\n" +
					"    test(i-1);               /* Let phil. on left eat if possible */\n" +
					"    test(i+1);               /* Let phil. on rght eat if possible */\n" +
					"    UP(me);                  /* up critical section */\n" +
					"   }\n" +
					"The protocol is fairly elaborate, and Tannenbaum's presentation is made more subtle by its coding style."
        };
        JComboBox cb = (JComboBox)this.cBxMethod;
        int index = cb.getSelectedIndex();
        JOptionPane.showMessageDialog(null,detailMethod[index], method[index], JOptionPane.INFORMATION_MESSAGE);
    }

    private void cBxNumOfPhilosopherActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox cb = (JComboBox)evt.getSource();
        int num = cb.getSelectedIndex() + 2;
        numberOfPhilosopher = num;
        //System.out.println(num);
    }
 
   private void cBxMethodActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox cb = (JComboBox)evt.getSource();
        int num = cb.getSelectedIndex();
        numberOfMethod = num;
        //System.out.println(num);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DiningDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiningDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiningDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiningDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiningDisplay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetailMethod;
    private javax.swing.JButton btnSet;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox<String> cBxMethod;
    private javax.swing.JComboBox<String> cBxNumOfPhilosopher;
    private java.awt.Canvas canvas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEating;
    private javax.swing.JLabel lblThinking;
    private javax.swing.JLabel lblWaiting;
    private java.awt.Panel panel1;
    private javax.swing.JSlider sliderEating;
    private javax.swing.JSlider sliderThinking;
    private javax.swing.JSlider sliderWaiting;
    private javax.swing.JTextArea txtStatus;
    // End of variables declaration//GEN-END:variables
}
