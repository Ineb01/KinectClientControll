package at.ac.htlsteyr.bbachmay.kinect;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

import at.restental.andreas.networking.RPIClient;

public class KinectMain extends J4KSDK {
	
	static RPIClient sock;
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) {
		System.out.println("S");
		
		int skeletonID = 0;
        for(int i=0;i<skeleton_tracked.length;i++ ) {
        	if(skeleton_tracked[i]) {
        		skeletonID = i;
        	}
        }
        
        Skeleton skeletons[]=new Skeleton[getMaxNumberOfSkeletons()];
        for(int i=0;i<getMaxNumberOfSkeletons();i++)
          skeletons[i]=Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this);
        
        double[] ellbowR = skeletons[skeletonID].get3DJoint(9);
        double[] wristR = skeletons[skeletonID].get3DJoint(10);
        Double angleR1;
        Double angleR2;
        
        if((ellbowR[1]-wristR[1]) > 0) {
        	angleR1 = Math.PI + Math.atan((ellbowR[0]-wristR[0])/(ellbowR[1]-wristR[1]));
        } else {
        	angleR1 = Math.atan((ellbowR[0]-wristR[0])/(ellbowR[1]-wristR[1]));
        }
        if(angleR1>Math.PI) {
        	angleR1 = -Math.PI*2 + angleR1;
        }
        
        if((ellbowR[1]-wristR[1]) > 0) {
        	angleR2 = Math.PI + Math.atan((ellbowR[2]-wristR[2])/(ellbowR[1]-wristR[1]));
        } else {
        	angleR2 = Math.atan((ellbowR[2]-wristR[2])/(ellbowR[1]-wristR[1]));
        }
        if(angleR2>Math.PI) {
        	angleR2 = -Math.PI*2 + angleR2;
        }
        
        angleR1 /= Math.PI*2;
        angleR2 /= Math.PI*2;
        angleR1 *= (float)-360;
        angleR2 *= (float)-360;        
        
        double[] ellbowL = skeletons[skeletonID].get3DJoint(5);
        double[] wristL = skeletons[skeletonID].get3DJoint(6);
        Double angleL1;
        Double angleL2;
        if((ellbowL[1]-wristL[1]) > 0) {
        	angleL1 = Math.PI + Math.atan((ellbowL[0]-wristL[0])/(ellbowL[1]-wristL[1]));
        } else {
        	angleL1 = Math.atan((ellbowL[0]-wristL[0])/(ellbowL[1]-wristL[1]));
        }
        if(angleL1>Math.PI) {
        	angleL1 = -Math.PI*2 + angleL1;
        }
        
        if((ellbowL[1]-wristL[1]) > 0) {
        	angleL2 = Math.PI + Math.atan((ellbowL[2]-wristL[2])/(ellbowL[1]-wristL[1]));
        } else {
        	angleL2 = Math.atan((ellbowL[2]-wristL[2])/(ellbowL[1]-wristL[1]));
        }
        if(angleL2>Math.PI) {
        	angleL2 = -Math.PI*2 + angleL2;
        }
        
        angleL1 /= Math.PI*2;
        angleL2 /= Math.PI*2;
        angleL1 *= (float)-360;
        angleL2 *= (float)-360;        
        
        sock.println(angleL2.intValue() + " " + angleR2.intValue());
        sock.stream.flush();

    } 

	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		//System.out.println("C");
	}

	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
		//System.out.println("D");
	}
	
	public static void main(String[] args) {
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		//ControllGUI gui = new ControllGUI("Roboter");
		
		
		try {
			sock = new RPIClient(3344, "192.168.43.105");
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			System.out.println("WARNING: You are running a 32bit version of Java.");
			System.out.println("This may reduce significantly the performance of this application.");
			System.out.println("It is strongly adviced to exit this program and install a 64bit version of Java.\n");
		}
		
		KinectMain kinect = new KinectMain();
		kinect.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.SKELETON);

		while(s == null) {
			try {
				s = stdin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		sock.println("end");
		sock.close();
		kinect.stop();
		
		return;
	}

	
}
