import java.util.Scanner;

public class Programm {
	static String Type;
	public static void main(String[] args) {
		do {

            Scanner in = new Scanner(System.in);
            System.out.println("Put in the ex�el file path, for example E:\\catalog\\address.xls . ������� ���� �� excel ����� : ");
            String Input = in.nextLine();

       
        DataSearch dataSearch = new DataSearch();
        dataSearch.collectData(Input);
        
        System.out.println("Type 'Any char' to continue, for example 'n';    'y' to exit.");
       
        Type = in.next();
    } while (!(Type.equals("y")));

    }
}
