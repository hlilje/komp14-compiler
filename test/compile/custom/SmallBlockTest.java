/* A small test to see if a variable is found after a nested block */
// EXT:NBD

class SmallBlockTest {
    public static void main(String[] args) {
        int i;
        i = 0;
        {
            int j;
            j = 0;
            {
                int k;
                k = 0;
                System.out.println(i);
                System.out.println(j);
                System.out.println(k);
                {
                    // Empty
                }
            }
            System.out.println(i);
            System.out.println(j);
            {
                int k;
            }
            System.out.println(i);
            System.out.println(j);
        }
        System.out.println(i);
    }
}

