package Team102Lib;

import java.util.ArrayDeque;
/**
 *
 * @author Administrator
 */
public class MovingStat{

	int n;			// The size of the moving window on to the data points.
	public double average;		// The computed moving average.
	public double variance;	// The moving variance.
	public double stdDev;		// The moving standard deviation.
	ArrayDeque<Double> dataQueue;
	private double m_oldS;

	
	public MovingStat(int windowSize)
	{
		this.n = windowSize;
		this.average = 0.0;
		this.variance = 0.0;
		this.stdDev = 0.0;
		dataQueue = new ArrayDeque<Double>(windowSize);
	}
	
	public void update(double newDataPoint)
	{
		double oldAvg = this.average;						// Save the previous average.
		int numPoints = dataQueue.size();
		if(numPoints == 0)
		{
			average = newDataPoint;
			//variance = 0.0;
			variance = 50000.0;
			stdDev = 0.0;
			m_oldS = 0.0;
			dataQueue.offerFirst(newDataPoint);		// Add the newest point.
		}
		else if(numPoints != this.n)			// if there are not enough points, calculate the average, etc the simple way.
		{
			dataQueue.offerFirst(newDataPoint);		// Add the newest point.
			numPoints++;
			double newAvg = oldAvg + (newDataPoint - oldAvg) / numPoints;
            double m_newS = m_oldS + (newDataPoint - oldAvg) * (newDataPoint - newAvg);  
			m_oldS = m_newS;
			
		}
		else
		{
			// keep the queue up-to-date with the right number of elements.
			double oldestDataPoint = dataQueue.pollLast();		// Remove the oldest point and remember it.
			dataQueue.offerFirst(newDataPoint);					// Add the newest point.

			// Calculate the moving average, etc.
			double newAvg = oldAvg + (newDataPoint - oldestDataPoint) / this.n;
			this.average = newAvg;
			this.variance += (newDataPoint - oldestDataPoint) * (newDataPoint - newAvg + oldestDataPoint - oldAvg) / (this.n - 1);
			this.stdDev = Math.sqrt(variance);
		}
		return;
	}
	
	// Tells if we have enough points to return stats.
	public boolean hasStats()
	{
		return dataQueue.size() == this.n;
	}
}