package social_network.ui;

import social_network.domain.Entity;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.exceptions.DuplicateException;
import social_network.exceptions.FriendshipException;
import social_network.exceptions.LackException;
import social_network.exceptions.ValidationException;

import social_network.service.ServiceUser;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UI<ID, E extends Entity<ID>> {
    private final ServiceUser serviceUser;

    public UI(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    private void uiAddUser() {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert ID of user: ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        input.nextLine();
        System.out.print("First name: ");
        String firstName = input.nextLine();
        System.out.print("Last name: ");
        String lastName = input.nextLine();
        System.out.print("Age: ");
        Integer age = Integer.valueOf(input.nextLine());
        String username = input.nextLine();
        String password = input.nextLine();

        try {
            serviceUser.saveUser(id, firstName, lastName, age, username, password);
        } catch (ValidationException | IllegalArgumentException | DuplicateException |
                 FileNotFoundException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
            return;
        }
        System.out.println("\033[0;32m" + "User added successfully!" + "\033[0m");
    }

    private void uiUserList() {
        try {

            List<User> userList1 = new ArrayList<>();
            Iterable<User> userList = serviceUser.findAll();
            for (User user : userList) {
                userList1.add(user);
            }
            userList1.sort(Comparator.comparing(User::getId));
            for (User user : userList1) {
                System.out.println(user);
            }
        } catch (LackException e) {
            System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
            return;
        }
        System.out.println();
    }

    private void uiDeleteUser() {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert ID of user: ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        try {
            serviceUser.deleteUser(id);
        } catch (ValidationException | IllegalArgumentException | LackException | FileNotFoundException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
            return;
        }
        System.out.println("\033[0;32m" + "User deleted successfully!" + "\033[0m");
    }

    private void uiAddFriend() {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert ID of first user: ");
        Long id1;
        try {
            id1 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        System.out.print("Insert ID of second user: ");
        Long id2;
        try {
            id2 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        input.nextLine();
        try {
            /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));*/
            serviceUser.saveFriend(id1, id2);
        } catch (ValidationException | LackException | FriendshipException | DuplicateException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("\033[0;32m" + "Friendship established successfully!" + "\033[0m");

    }

    private void uiDeleteFriend() {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert ID of first user: ");
        Long id1;
        try {
            id1 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        System.out.print("Insert ID of second user: ");
        Long id2;
        try {
            id2 = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        input.nextLine();
        try {
            serviceUser.deleteFriend(id1, id2);
        } catch (ValidationException | LackException | DuplicateException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
            return;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\033[0;32m" + "The 2 users banned each other for good!" + "\033[0m");

    }

    private void uiFriendList() {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert ID of user: ");
        Long id;
        try {
            id = input.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("\033[0;31m" + "Id must contain only digits!" + "\033[0m");
            return;
        }
        input.nextLine();
        try {
            Iterable<User> friends = serviceUser.friendList(id);
            String firstName = serviceUser.findOne(id).getFirstName();
            String lastname = serviceUser.findOne(id).getLastName();
            System.out.println("\033[0;32m" + "Friend list of " + firstName + " " + lastname + ":" + "\033[0m");
            for (User user : friends) {
                System.out.println(user);
            }
            System.out.println("\033[0;32m" + "The list of friends has been successfully showed!\n" + "\033[0m");

        } catch (LackException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
        }
    }

    private void uiNumberOfCommunities() {
        try {
            int numberOfCommunities = serviceUser.numberOfCommunities();
            System.out.println("\033[0;32m" + "Number of communities is: " + numberOfCommunities + "\033[0m");
        } catch (LackException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
        }
    }

    private void uiMostSocialCommunity() {
        try {
            List<User> mostSocialCommunity = serviceUser.mostSocialCommunityOfSocialNetwork();
            System.out.println("\033[0;32m" + "Most social community is:" + "\033[0m");
            for (User user : mostSocialCommunity) {
                System.out.println(" -> " + user + " <- ");
            }
        } catch (LackException exception) {
            System.out.println("\033[0;31m" + exception.getMessage() + "\033[0m");
        }

    }

    //
    private void uiFriendShipList() {
        try {
            List<Friendship> friendshipList = serviceUser.getFriendships();
            if (friendshipList.size() > 0)
                for (Friendship f : friendshipList) {
                    System.out.println("\033[0;33m" + "Friendship " + f.getId() + " created at:" + f.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\033[0m");
                    System.out.println("User with ID: " + f.getId1() + " is friend with user with ID: " + f.getId2());
                    System.out.println();
                }
            else {
                System.out.println("\033[0;31m" + "No friendships were made!\n" + "\033[0m");
            }
        } catch (FriendshipException e) {
            System.out.println("\033[0;31m" + e.getMessage() + "\033[0m");
            return;
        }
        System.out.println();
    }

    private void menu() {
        System.out.println("\u001B[36m" + "\n~~~~~~~Social Network~~~~~~~" + "\u001B[0m");
        System.out.println("1.Add a user.");
        System.out.println("2.Delete a user.");
        System.out.println("3.Show list of users.");
        System.out.println("4.Add friend for a user.");
        System.out.println("5.Delete friend from a user.");
        System.out.println("6.Show list of friendships.");
        System.out.println("7.Number of communities.");
        System.out.println("8.Most social community.");
        System.out.println("9.List of friendships");
        System.out.println("10.Exit");

    }

    public void run() {
        Scanner input = new Scanner(System.in);
        boolean finished = false;
        while (!finished) {
            try {
                menu();
                System.out.print("Command 👉 ");
                String command = input.next();
                if (command.equals("1")) {
                    uiAddUser();
                }
                if (command.equals("2")) {
                    uiDeleteUser();
                }
                if (command.equals("3")) {
                    uiUserList();
                }
                if (command.equals("4")) {
                    uiAddFriend();
                }
                if (command.equals("5")) {
                    uiDeleteFriend();
                }
                if (command.equals("6")) {
                    uiFriendList();
                }
                if (command.equals("7")) {
                    uiNumberOfCommunities();
                }
                if (command.equals("8")) {
                    uiMostSocialCommunity();
                }
                if (command.equals("9")) {
                    uiFriendShipList();
                }
                if (command.equals("Exit") || command.equals("10")) {
                    finished = true;
                }

            } catch (RuntimeException exception) {
                System.out.println("\033[0;31m" + "Invalid command!" + "\033[0m");
            }
        }
    }
}
