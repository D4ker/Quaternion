import static java.lang.Math.*;

public final class Quaternion {

    private double w, x, y, z; // Скалярная и векторные части

    // Построение Кватерниона через угол поворота и ось вращения
    public Quaternion(double angle, double x, double y, double z) {
        final double halfAngle = angle / 2;
        w = cos(halfAngle);
        if (x != 0 || y != 0 || z != 0) {
            final double newSin = sin(halfAngle);
            final double vectorLength = sqrt(x * x + y * y + z * z);
            this.x = x / vectorLength * newSin;
            this.y = y / vectorLength * newSin;
            this.z = z / vectorLength * newSin;
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    // Построение Кватерниона на основе уже имеющегося Кватерниона
    public Quaternion(Quaternion q) {
        this(q.x, q.y, q.z);
        w = q.w;
    }

    // Построение Кватерниона через ось
    public Quaternion(double x, double y, double z) {
        w = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Метод-конструктор Кватерниона через комплексные числа
    public Quaternion complexQuaternion(double w, double x, double y, double z) {
        Quaternion newQuaternion = new Quaternion(x, y, z);
        newQuaternion.w = w;
        return newQuaternion;
    }

    // Описание методов доступа к полям Кватерниона
    public double getAngle() {
        return 2 * acos(w);
    }

    public double getW() {
        return w;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Возвращает скалярную часть Кватерниона
    public double getScalar() {
        return w;
    }

    // Возвращает векторную часть Кватерниона в виде массива
    public double[] getVector() {
        return new double[]{x, y, z};
    }

    // Возвращает векторную часть Кватерниона в виде строки
    public String getVectorString() {
        return String.format("%+fi%+fj%+fk", x, y, z);
    }

    // Описание методов изменения полей Кватерниона
    public void setAngle(double angle) {
        w = cos(angle / 2);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    // Возвращает сопряженный Кватернион
    public Quaternion conjugate() {
        return complexQuaternion(w, -x, -y, -z);
    }

    // Возвращает модуль Кватерниона
    public double module() {
        return sqrt(w * w + x * x + y * y + z * z);
    }

    // Деление (обращение) Кватерниона
    public Quaternion divide() {
        final double module = module();
        if (module == 0) return this;
        final double squareModule = module * module;
        return complexQuaternion(w / squareModule, -x / squareModule, -y / squareModule, -z / squareModule);
    }

    // Перемножение Кватернионов
    public Quaternion multiply(Quaternion other) {
        final double newW = w * other.w - x * other.x - y * other.y - z * other.z;
        final double newX = w * other.x + x * other.w + y * other.z - z * other.y;
        final double newY = w * other.y - x * other.z + y * other.w + z * other.x;
        final double newZ = w * other.z + x * other.y - y * other.x + z * other.w;
        return complexQuaternion(newW, newX, newY, newZ);
    }

    // Умножение Кватерниона на скаляр
    public Quaternion multiply(double value) {
        return complexQuaternion(w * value, x * value, y * value, z * value);
    }

    // Сложение Кватернионов
    public Quaternion add(Quaternion other) {
        return complexQuaternion(w + other.w, x + other.x, y + other.y, z + other.z);
    }

    // Вычитание Кватернионов
    public Quaternion deduct(Quaternion other) {
        return complexQuaternion(w - other.w, x - other.x, y - other.y, z - other.z);
    }

    // Нормирование Кватерниона
    public Quaternion normalize() {
        final double length = module();
        if (length == 0) return this;
        return complexQuaternion(w / length, x / length, y / length, z / length);
    }

    // Сравнение Кватернионов
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof Quaternion) {
            Quaternion other = (Quaternion) object;
            return w == other.w && x == other.x && y == other.y && z == other.z;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(w);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    // Строковое представление Кватерниона
    @Override
    public String toString() {
        return String.format("%f%+fi%+fj%+fk", w, x, y, z);
    }
}
