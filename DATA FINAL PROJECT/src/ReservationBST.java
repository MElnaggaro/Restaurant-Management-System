import java.util.ArrayList;

public class ReservationBST {
    private class Node {
        Reservation reservation;
        Node left, right;

        public Node(Reservation reservation) {
            this.reservation = reservation;
            left = right = null;
        }
    }

    private Node root;

    public ReservationBST() {
        root = null;
    }

    public void insert(Reservation reservation) {
        root = insertRec(root, reservation);
    }

    private Node insertRec(Node root, Reservation reservation) {
        if (root == null) {
            root = new Node(reservation);
            return root;
        }

        if (reservation.getReservationID() < root.reservation.getReservationID()) {
            root.left = insertRec(root.left, reservation);
        } else if (reservation.getReservationID() > root.reservation.getReservationID()) {
            root.right = insertRec(root.right, reservation);
        }
        return root;
    }

    public Reservation search(int reservationID) {
        return searchRec(root, reservationID);
    }

    private Reservation searchRec(Node root, int reservationID) {
        if (root == null) {
            return null;
        }

        if (root.reservation.getReservationID() == reservationID) {
            return root.reservation;
        }

        if (reservationID < root.reservation.getReservationID()) {
            return searchRec(root.left, reservationID);
        } else {
            return searchRec(root.right, reservationID);
        }
    }

    public void delete(int reservationID) {
        root = deleteRec(root, reservationID);
    }

    private Node deleteRec(Node root, int reservationID) {
        if (root == null) {
            return root;
        }

        if (reservationID < root.reservation.getReservationID()) {
            root.left = deleteRec(root.left, reservationID);
        } else if (reservationID > root.reservation.getReservationID()) {
            root.right = deleteRec(root.right, reservationID);
        } else {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left == null) {
                root = root.right;
            } else if (root.right == null) {
                root = root.left;
            } else {
                Node predecessor = root.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }

                root.reservation = predecessor.reservation;
                root.left = deleteRec(root.left, predecessor.reservation.getReservationID());
            }
        }

        return root;
    }

    public void printInOrder() {
        System.out.println("Inorder Traversal:");
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.reservation);
            inorderRec(root.right);
        }
    }

    public void printPreOrder() {
        preorderRec(root);
    }

    private void preorderRec(Node root) {
        if (root != null) {
            System.out.println(root.reservation);
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }
    public void printPostOrder() {
        postorderRec(root);
    }
    public ArrayList<Reservation> toArrayList() {
        ArrayList<Reservation> list = new ArrayList<>();
        inorderToArrayList(root, list);
        return list;
    }

    private void inorderToArrayList(Node root, ArrayList<Reservation> list) {
        if (root != null) {
            inorderToArrayList(root.left, list);
            list.add(root.reservation);
            inorderToArrayList(root.right, list);
        }
    }


    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.println(root.reservation);
        }
    }
}