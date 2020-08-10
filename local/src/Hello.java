
import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class Hello {
	private void say(String what) {
		System.out.println("say :" + what);
	}
	private String myParam;
	public static void main(String[] args) {
		Person person = new Person();
		person.setPersonName("高校生");
		if (person != null) {
			System.out.println("person :" + person.getPersonName());
		}
		System.out.println("这是我手打的类，Hello！！！");
	}
}