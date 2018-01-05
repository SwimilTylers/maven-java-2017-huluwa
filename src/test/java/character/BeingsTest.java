package character;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BeingsTest {
    @org.junit.Test
    public void tellBasePosition() throws Exception {
    }

    @org.junit.Test
    public void jumpTO() throws Exception {
    }

    @org.junit.Test
    public void challenge() throws Exception {
    }

    @org.junit.Test
    public void jumpTOAndChallenge() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Beings.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
