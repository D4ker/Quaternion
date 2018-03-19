import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void getInverse() {
        assertArrayEquals(new double[]{0.7071, -0.7071, 0, 0}, firstQ.getInverse().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 1, 0}, secondQ.getInverse().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0, -1}, thirdQ.getInverse().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0.7071, 0.7071}, firstQ.multiply(secondQ).getInverse().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 0.5, -0.5}, secondQ.add(thirdQ).getInverse().get(), 1e-5);
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
        assertArrayEquals(new double[]{0, 1, 0}, secondQ.getInverse().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0, 1}, thirdQ.normalize().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0.7071, 0.7071}, firstQ.multiply(secondQ).getInverse().getVector(), 1e-5);
        assertArrayEquals(new double[]{0, 0.5, -0.5}, secondQ.add(thirdQ).getInverse().getVector(), 1e-5);
    }

    @Test
    public void conjugate() {
        assertArrayEquals(new double[]{0, 0, 0, -1}, thirdQ.normalize().conjugate().get(), 1e-5);
        assertArrayEquals(new double[]{0, 0, -0.7071, -0.7071}, firstQ.multiply(secondQ).getInverse().conjugate().get(), 1e-5);
    }

    @Test
    public void module() {
        assertEquals(1, thirdQ.normalize().module(), 1e-5);
        assertEquals(1, firstQ.multiply(secondQ).getInverse().module(), 1e-5);
        assertEquals(0.7071, secondQ.add(thirdQ).getInverse().module(), 1e-5);
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
    public void equals() {
        assertEquals(new Quaternion(PI / 2, 1, 0, 0), firstQ);
        assertEquals(new Quaternion(3 * PI, 0, 1, 0), secondQ);
        assertTrue(thirdQ.equals(Quaternion.create(0, 0, 0, 1), 1e-5));
    }

    @Test
    public void getW() {
        assertEquals(0.7071, firstQ.getW(), 1e-5);
    }

    @Test
    public void getX() {
        assertEquals(0.7071, firstQ.getX(), 1e-5);
    }

    @Test
    public void getY() {
        assertEquals(0, firstQ.getY());
    }

    @Test
    public void getZ() {
        assertEquals(0, firstQ.getZ());
    }

    @Test
    public void setW() {
        firstQ.setW(1);
        assertEquals(1, firstQ.getW());
    }

    @Test
    public void setX() {
        firstQ.setX(1);
        assertEquals(1, firstQ.getX());
    }

    @Test
    public void setY() {
        firstQ.setY(1);
        assertEquals(1, firstQ.getY());
    }

    @Test
    public void setZ() {
        firstQ.setZ(1);
        assertEquals(1, firstQ.getZ());
    }
}
