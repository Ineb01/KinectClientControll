package at.ac.htlsteyr.bbachmay.kinect;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

import at.ac.htlsteyr.bbachmay.kinect.gui.ControllGUI;
import at.restental.andreas.networking.RPIClient;

public class KinectMain extends J4KSDK {

	static RPIClient sock;
	static ControllGUI gui = new ControllGUI();

	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation,
			byte[] joint_status) {

		int skeletonID = 0;
		for (int i = 0; i < skeleton_tracked.length; i++) {
			if (skeleton_tracked[i]) {
				skeletonID = i;
			}
		}

		Skeleton skeletons[] = new Skeleton[getMaxNumberOfSkeletons()];
		for (int i = 0; i < getMaxNumberOfSkeletons(); i++)
			skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status,
					this);

		double[] ellbowR = skeletons[skeletonID].get3DJoint(9);
		double[] wristR = skeletons[skeletonID].get3DJoint(10);
		Double angleRight;

		if ((ellbowR[1] - wristR[1]) > 0) {
			angleRight = Math.PI + Math.atan((ellbowR[2] - wristR[2]) / (ellbowR[1] - wristR[1]));
		} else {
			angleRight = Math.atan((ellbowR[2] - wristR[2]) / (ellbowR[1] - wristR[1]));
		}
		if (angleRight > Math.PI) {
			angleRight = -Math.PI * 2 + angleRight;
		}

		angleRight /= Math.PI * 2;
		angleRight *= (float) -360;

		double[] ellbowL = skeletons[skeletonID].get3DJoint(5);
		double[] wristL = skeletons[skeletonID].get3DJoint(6);
		Double angleLeft;
		if ((ellbowL[1] - wristL[1]) > 0) {
			angleLeft = Math.PI + Math.atan((ellbowL[2] - wristL[2]) / (ellbowL[1] - wristL[1]));
		} else {
			angleLeft = Math.atan((ellbowL[2] - wristL[2]) / (ellbowL[1] - wristL[1]));
		}
		if (angleLeft > Math.PI) {
			angleLeft = -Math.PI * 2 + angleLeft;
		}

		angleLeft /= Math.PI * 2;
		angleLeft *= (float) -360;

		if (gui.frozen() == false) {
			sock.out.println(angleLeft.intValue() + " " + angleRight.intValue());
			sock.out.flush();
		}

	}

	@Override
	public void onColorFrameEvent(byte[] color_frame) {

	}

	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {

	}

	public static void main(String[] args) {

		try {
			sock = new RPIClient(3344, "192.168.43.105");
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		if (System.getProperty("os.arch").toLowerCase().indexOf("64") < 0) {
			System.out.println("WARNING: You are running a 32bit version of Java.");
			System.out.println("This may reduce significantly the performance of this application.");
			System.out.println("It is strongly adviced to exit this program and install a 64bit version of Java.\n");
		}

		KinectMain kinect = new KinectMain();
		kinect.start(J4KSDK.COLOR | J4KSDK.DEPTH | J4KSDK.SKELETON);

		while (gui.is_exit()) {
		}

		sock.out.println("end");
		sock.out.flush();
		sock.cleanup();
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		kinect.stop();

		return;
	}

}
