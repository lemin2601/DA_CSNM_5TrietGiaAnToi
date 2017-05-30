package dining;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import gfx.Assets;

/**
 *
 * @author Lê Minh Trung 13T4
 */
public class DiningDisplay extends javax.swing.JFrame implements Runnable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// dining chưa thông tin của các philo
 	DiningState state;	 	
	
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
 			//	System.out.println("Ticks and Frames: " + ticks);
 				ticks = 0;
 				timer = 0;
 			}
 		}
 		

 	}
 	// khởi tạo ban đầu
 	private void init(){
 		Assets.init();

 		state = new DiningState(DiningState.method);
 		state.start();
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
 			state.stop();
 	 		thread.stop();
 	 		
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
        cBxMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monitor Solution", "Semaphore solution", "Normal solution"}));
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
    	state.stop();
    	DiningState.timeEatingRandom = timeEating;
    	DiningState.timeThinkingRandom = timeThinking;
    	DiningState.timeWaitingRandom = timeWaiting;
    	DiningState.numOfPhilosopher = numberOfPhilosopher;
    	switch (numberOfMethod) {
    	case 0:
			DiningState.method = DiningState.Method.MONITOR;	
			break;
    	case 1:
			DiningState.method = DiningState.Method.SEMAPHORE;	
			break;
    	case 2:
			DiningState.method = DiningState.Method.NORMAL;	
			break;

		default:
			break;
		}
    	
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
        String method[] ={"Monitor Solution",
                        "Semaphore Solution",
                        "Normal"};
        String detailMethod[] = {
        		"Monitor Solution\nSorry,function is compliting",
                "Semaphore Solution\nSorry,function is compliting",
                "Normal\nSorry,function is compliting"
        		
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
