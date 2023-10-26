// Author: John Ramirez
// Demonstration of the 8-Queens problem

// This handout shows interactively how the 8-Queens problem can be solved
// using recursion and backtracking (with exhaustive search with pruning).

// As far as this code goes, some improvements can definitely be made,
// especially with regard to the interface and the flexibility for the
// user.  However, it does an adequate job of showing the steps involved in
// solving the 8-Queens problem.

//To run the program: java JRQueens <delay in milliseconds>

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class JRQueens extends JFrame implements Runnable
{
	public ChessSquare [][] squares;
	public boolean [] saferow;   // is the row empty?  true or false
	public boolean [] safeleftdiag; // is the left (from upper right to lower left)
									// diagonal unthreatened?  true or false
	public boolean [] saferightdiag; // is the right (from upper left to lower right)
									 // diagonal unthreatened?  true or false

    private ShapePanel drawPanel; // panel for the board to be drawn -- see details
								  // in class definition below
	private JLabel info;		// informative label
	private JButton runDemo;	// button to allow interaction
	private Checkbox checkbox1;
	private Thread runThread;	// thread to allow "motion"
	private int delay;			// delay between moves
	private PauseClass pauser;	// class to allow execution to pause in between
								// solutions -- see details in definition below
	private boolean paused;		// is execution paused?  true or false
	private int sol, calls;

     public JRQueens(int delta)
     {
            super("Interactive 8 Queens Problem");
			delay = delta;
            drawPanel = new ShapePanel(1000, 1000);

            runDemo = new JButton("Next Solution");		// Set up button
            ButtonHandler bhandler = new ButtonHandler();
            runDemo.addActionListener(bhandler);

            info = new JLabel("The 8 Queens Problem", (int) CENTER_ALIGNMENT);
		 	checkbox1 = new Checkbox("Pause on each Queen");    
			checkbox1.setBounds(100, 100,  50, 50);   

			JPanel south = new JPanel();			// Set up layout
			south.add(runDemo, BorderLayout.EAST);
			south.add(checkbox1, BorderLayout.WEST);

            Container c = getContentPane();			// Set up layout
            c.add(drawPanel, BorderLayout.CENTER);
            c.add(info, BorderLayout.NORTH);
            // c.add(runDemo, BorderLayout.SOUTH);
			c.add(south, BorderLayout.SOUTH);

		    squares = new ChessSquare[8][8];	// initialize variables
			saferow = new boolean[8];
			safeleftdiag = new boolean[15];
			saferightdiag = new boolean[15];
			int size = 110;
			int offset = 25;
			for (int row = 0; row < 8; row++)
			{
				saferow[row] = true;  // all rows are safe
				for (int col = 0; col < 8; col++)
				{
					squares[row][col] = new ChessSquare(offset + col*size, 
														offset + row*size, 
						                                size, size);
				}
			}
			for (int i = 0; i < 15; i++)
			{                               // initialize all diagonals to safe
				 safeleftdiag[i] = true;
				 saferightdiag[i] = true;
			}
			sol = 0;
			calls = 0;

            runThread = null;

            setSize(1000, 1000);
            setVisible(true);
     }

	// Is the current location safe?  We check the row and both diagonals.
	// The column does not have to be checked since our algorithm proceeds in
	// a column by column manner.
	public boolean safe(int row, int col)
	{
		return (saferow[row] && safeleftdiag[row+col] &&
			saferightdiag[row-col+7]);
	}

	// This recursive method does most of the work to solve the problem.  Note
	// that it is called for each column tried in the board, but, due to
	// backtracking, will overall be called many many times.  Each call is from
	// the point of view of the current column, col.
	public void trycol(int col)
	{
		calls++; // increment number of calls made
		for (int row = 0; row < 8; row++)    // try all rows if necessary
		{
			// This test is what does the "pruning" of the execution tree --
			// if the location is not safe we do not bother to make a recursive
			// call from that position, saving overall many thousands of calls.
			// See notes from class for details.
			if (safe(row, col))
			{
				// if the current position is free from a threat, put a queen
				// there and mark the row and diags as unsafe
				saferow[row] = false;
				safeleftdiag[row+col] = false;
				saferightdiag[row-col+7] = false;
				(squares[row][col]).occupy();
				if(checkbox1.getState()){
					runDemo.setEnabled(true);
					pauser.pause();
				}

				repaint();
				
				if (col == 7)      // queen safely on every column, announce
				{                  // solution and pause execution
					sol++;
					info.setText("Solution " + sol + " Found After " + calls + " Calls");
					runDemo.setText("Click to Continue");
					runDemo.setEnabled(true);
					pauser.pause();
				}
				else
				{
					// Still more columns left to fill, so delay a bit and then
					// try the next column recursively
					try
					{
						Thread.sleep(delay);
					}
					catch (InterruptedException e)
					{
						System.out.println("Thread error B");
					}

					trycol(col+1);  // try to put a queen onto the next column
				}

				saferow[row] = true;               // remove the queen from
				safeleftdiag[row+col] = true;      // the current row and
				saferightdiag[row-col+7] = true;   // unset the threats. The
				(squares[row][col]).remove();      // loop will then try the
		                                           // next row down
			}	
		}
		// Once all rows have been tried, the method finishes, and execution
		// backtracks to the previous call.
	}

	// This method executes implicitly when the Thread is started.  Note that
	// its main activity is the call trycol(0), which starts the recursive
	// algorithm on its way.
    public void run()
    {
		paused = false;
		pauser = new PauseClass();
        trycol(0);
		repaint();
        info.setText("Program Completed: " + sol + " Solutions, "
			                               + calls + " Calls, "
			                               + (8*calls) + " iterations ");
    }

    public static void main(String [] args)
    {
		// Use the delay value entered by the user, or use 100 if no
		// value is entered.
		int delay;
		if (args != null && args.length >= 1)
			delay = Integer.parseInt(args[0]);
		else
			delay = 100;
        JRQueens win = new JRQueens(delay);

        win.addWindowListener(
            new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                { System.exit(0); }
            }
        );
    }

	// This class is used to enable the execution to pause between
	// solutions.  The Java details of this class have to do with monitors
	// and synchronized Threads and are beyond the scope of the CS 0445
	// course.  However, if you are interested in learning more about these
	// Java features, feel free to look them up in the Java API.
	private class PauseClass
	{
		public synchronized void pause()
		{
			paused = true;
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				System.out.println("Pause Problem");
			}
		}

		public synchronized void unpause()
		{
			paused = false;
			notify();
		}
	}

	// Class to handle button.  For more on the Java details, see
	// the API online.
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == runDemo)
			{
				if (!paused)
				{
					runDemo.setEnabled(false);
					info.setText("Searching for Solutions");
					runThread = new Thread(JRQueens.this);
					runThread.start();
				}
				else
				{
					runDemo.setEnabled(false);
					info.setText("Searching for Solutions");
					pauser.unpause();
				}
				repaint();
			}
		}
	}

	// Inner class to represent a square on the board.  This class extends
	// Rectangle2D.Double, which can be drawn graphically by the draw() method
	// of the Graphics2D class.  The main additions I have made in the subclass
	// are the occupied variable and the drawing of the Q if the space is
	// occupied.
	private class ChessSquare extends Rectangle2D.Double
	{
		private boolean occupied;

		public ChessSquare(double x1, double y1, double wid, double hei)
		{
			super(x1, y1, wid, hei);
			occupied = false;
		}

		public void draw(Graphics2D g2d)
		{
			g2d.draw(this);
			int x = (int) this.getX();
			int y = (int) this.getY();
			int sz = (int) this.getWidth();
			 
			if (occupied)
			{
				g2d.setFont(new Font("Serif", Font.BOLD, 70));
				g2d.drawString("Q", x+25, y+sz-30);
			}
		}

		public void occupy()
		{
			occupied = true;
		}
		public void remove()
		{
			occupied = false;
		}

		public boolean isOccupied()
		{
			return occupied;
		}
	}

	// Class that allows the board to be rendered in a nice way.
	// See that Java API or a good book on Java graphics for more
	// details about this class.
    private class ShapePanel extends JPanel
    {

        private int prefwid, prefht;

        public ShapePanel (int pwid, int pht)
        {

            prefwid = pwid;
            prefht = pht;
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(prefwid, prefht);
        }

        public void paintComponent (Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < 8; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					(squares[i][j]).draw(g2d);
				}
			}    
        }
    }

}


