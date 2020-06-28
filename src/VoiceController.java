/**
 * Class VoiceController emulates a voice controller (poorly so, due to obvious contstraints of the course)
 */
public class VoiceController extends AbstractController {

    public VoiceController(Device pDevice) {
        super(pDevice);
    }

    @Override
    public void next() {
        System.out.print("Voice command <next> received. ");
        super.next();
    }
}
