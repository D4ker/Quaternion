import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.Math.*;

public class QuaternionTest {
    private final Quaternion firstQ = new Quaternion(PI / 2, 1, 0, 0);
    private final Quaternion secondQ = new Quaternion(3 * PI, 0, 1, 0);
    private final Quaternion thirdQ = new Quaternion(PI, 0, 0, 1);

    @Test
    public void add() {
        assertArrayEquals(new double[]{0.7071, 0.7071, -1, 0}, firstQ.add(secondQ).get(), 1e-5);
        assertArrayEquals(new double[]{0.7071, 0.7071, 0, 1}, firstQ.add(thirdQ).get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -1, 1}, secondQ.add(thirdQ).get(), 1e-5);
    }

    @Test
    public void multiply() {
        assertArrayEquals(new double[]{0, 0, -0.7071, -0.7071}, firstQ.multiply(secondQ).get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.7071, 0.7071}, firstQ.multiply(thirdQ).get(), 1e-5);
        assertArrayEquals(new double[]{0, -1, 0, 0}, secondQ.multiply(thirdQ).get(), 1e-5);
        assertArrayEquals(new double[]{0.35355, 0.35355, 0, 0}, firstQ.multiply(0.5).get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.2, 0.2}, secondQ.add(thirdQ).multiply(0.2).get(), 1e-5);
    }

    @Test
    public void deduct() {
        assertArrayEquals(new double[]{0.7071, 0.7071, 1, 0}, firstQ.deduct(secondQ).get(), 1e-5);
        assertArrayEquals(new double[]{0.7071, 0.7071, 0, -1}, firstQ.deduct(thirdQ).get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -1, -1}, secondQ.deduct(thirdQ).get(), 1e-5);
    }

    @Test
    public void normalize() {
        assertArrayEquals(new double[]{0.7071, 0.7071, 0, 0}, firstQ.normalize().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -1, 0}, secondQ.normalize().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0, 1}, thirdQ.normalize().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.7071, -0.7071}, firstQ.multiply(secondQ).normalize().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.7071, 0.7071}, secondQ.add(thirdQ).normalize().get(), 1e-5);
    }

    @Test
    public void divide() {
        assertArrayEquals(new double[]{0.7071, -0.7071, 0, 0}, firstQ.divide().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 1, 0}, secondQ.divide().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0, -1}, thirdQ.divide().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0.7071, 0.7071}, firstQ.multiply(secondQ).divide().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0.5, -0.5}, secondQ.add(thirdQ).divide().get(), 1e-5);
    }

    @Test
    public void getScalar() {
        assertEquals(0.7071, firstQ.getScalar(), 1e-5);
        assertEquals(0, secondQ.getScalar(), 1e-5);
        assertEquals(0, secondQ.add(thirdQ).getScalar(), 1e-5);
    }

    @Test
    public void getVector() {
        assertArrayEquals(new double[]{0.7071, 0, 0}, firstQ.getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 1, 0}, secondQ.divide().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 1}, thirdQ.normalize().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0.7071, 0.7071}, firstQ.multiply(secondQ).divide().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0.5, -0.5}, secondQ.add(thirdQ).divide().getVector(), 1e-5);
    }

    @Test
    public void conjugate() {
        assertArrayEquals(new double[]{0, 0, 0, -1}, thirdQ.normalize().conjugate().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.7071, -0.7071}, firstQ.multiply(secondQ).divide().conjugate().get(), 1e-5);
    }

    @Test
    public void module() {
        assertEquals(1, thirdQ.normalize().module(), 1e-5);
        assertEquals(1, firstQ.multiply(secondQ).divide().module(), 1e-5);
        assertEquals(0.7071, secondQ.add(thirdQ).divide().module(), 1e-5);
    }

    @Test
    public void Quaternion() {
        assertArrayEquals(new double[]{0.7071, 0.7071, 0, 0}, new Quaternion(PI / 2, 1, 0, 0).get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0, 0}, new Quaternion().get(), 1e-5);
        assertArrayEquals(firstQ.get(), new Quaternion(firstQ).get(), 1e-5);
    }

    @Test
    public void create() {
        assertArrayEquals(new double[]{0, 0, 1, 0}, Quaternion.create(0, 0, 1, 0).get(), 1e-5);
        assertArrayEquals(new double[]{1, 1, 0, 0}, Quaternion.create(1, 1, 0, 0).get(), 1e-5);
        assertArrayEquals(new double[]{0, 1, 0, 0}, Quaternion.create(0, 1, 0, 0).get(), 1e-5);
    }

    @Test
    public void setAngle() {
        firstQ.setAngle(7 * PI / 13);
        secondQ.setAngle(3 * PI / 2);
        thirdQ.setAngle(5 * PI);
        assertArrayEquals(new double[]{0.66312, 0.7071, 0, 0}, firstQ.get(), 1e-5);
        assertArrayEquals(new double[]{-0.7071, 0, -1, 0}, secondQ.get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0, 1}, thirdQ.get(), 1e-5);
    }
}
