import java.util.Scanner;

/**
 * The driver class
 */
public class TwoDTreeDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TwoDTree tree = new TwoDTree("CrimeLatLonXY.csv");
        System.out.println("Crime file loaded into 2d tree with "+ tree.size+" records.");
        boolean stop = false;
        while(!stop){
            System.out.println("What would you like to do?\n" +
                    "1: inorder\n" +
                    "2: preorder\n" +
                    "3: levelorder\n" +
                    "4: postorder\n" +
                    "5: reverseLevelOrder\n" +
                    "6: search for points within rectangle\n" +
                    "7: search for nearest neighbor\n" +
                    "8: quit");
            int i = sc.nextInt();
            sc.nextLine();
            switch(i){
                case 1:
                    tree.inOrderPrint();
                    break;
                case 2:
                    tree.preOrderPrint();
                    break;
                case 3:
                    tree.levelOrderPrint();
                    break;
                case 4:
                    tree.postOrderPrint();
                    break;
                case 5:
                    tree.reverseLevelOrderPrint();
                    break;
                case 6:
                    System.out.println("Enter a rectangle bottom left (X1,Y1) and top right (X2, Y2) as\n" +
                            "four doubles each separated by a space.");
                    String str6 = sc.nextLine();
                    String[] line = str6.split(" ");
                    System.out.println(tree.findPointsInRange(Double.parseDouble(line[0]),
                            Double.parseDouble(line[1]),Double.parseDouble(line[2]),Double.parseDouble(line[3])));
                    break;
                case 7:
                    System.out.println("Enter a point to find nearest crime. Separate with a space.");
                    String str7 = sc.nextLine();
                    String[] line7 = str7.split(" ");
                    System.out.println(tree.nearestNeighbor(Double.parseDouble(line7[0]),
                            Double.parseDouble(line7[1])));
                    break;
                case 8:
                    System.out.println("Thank you for exploring Pittsburgh crimes in the 1990’s.");
                    stop = true;
                    break;
            }


        }

    }
}
