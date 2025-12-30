package swingPlayer.example.RTSP_SwingPlayer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.freedesktop.gstreamer.*;
import org.freedesktop.gstreamer.elements.*;

public class RtspSwingPlayer {
	private static Pipeline pipeline;

    public static void main(String[] args) {

        // IMPORTANT: init GStreamer BEFORE Swing
        Gst.init("RTSP Swing Player", args);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RTSP Camera");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GstVideoComponent videoComponent = new GstVideoComponent();
            frame.add(videoComponent);

            frame.setSize(960, 540);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            startPipeline(videoComponent);
         // Clean up pipeline on exit
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    if (pipeline != null) {
                        pipeline.setState(State.NULL); // important
                    }
                }
            });
        });
    }
    
    private static void startPipeline(GstVideoComponent videoComponent) {

        pipeline = new Pipeline("rtsp-pipeline");

        Element src = ElementFactory.make("rtspsrc", "src");
        //you can set your own rtsp link here
        src.set("location", "rtsp://716f898c7b71.entrypoint.cloud.wowza.com:1935/app-8F9K44lJ/304679fe_stream2");  
        src.set("latency", 200);
        src.set("protocols", 4); // TCP

        Element depay = ElementFactory.make("rtph264depay", "depay");
        Element decodebin = ElementFactory.make("decodebin", "decode");
        Element convert = ElementFactory.make("videoconvert", "convert");

        pipeline.addMany(src, depay, decodebin, convert, videoComponent.getElement());

        // Link depay → decodebin dynamically
        depay.link(decodebin);

        // Handle dynamic pads of decodebin
        decodebin.connect((Element.PAD_ADDED) (Element element, Pad pad) -> {
            Pad sinkPad = convert.getStaticPad("sink");
            if (!sinkPad.isLinked()) {
                pad.link(sinkPad); // link decodebin output to videoconvert
            }
        });

        // Handle dynamic pads of rtspsrc
        src.connect((Element.PAD_ADDED) (Element element, Pad pad) -> {
            Pad sinkPad = depay.getStaticPad("sink");
            if (!sinkPad.isLinked()) {
                pad.link(sinkPad); // link rtspsrc to depay
            }
        });

        // Link videoconvert → appsink (GstVideoComponent)
        convert.link(videoComponent.getElement());

        pipeline.play();
    }


    
}
