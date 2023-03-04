package social_network.service;

import social_network.domain.Friendship;
import social_network.domain.FriendshipStatus;
import social_network.domain.User;
import social_network.domain.validator.UserValidator;
import social_network.domain.validator.Validator;
import social_network.exceptions.DuplicateException;
import social_network.exceptions.FriendshipException;
import social_network.exceptions.LackException;
import social_network.repository.memory.InMemoryRepository;
import social_network.service.Events.ChangeEventType;
import social_network.service.Events.UtilizatorChangeData;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import social_network.service.Observer.Observer;
import social_network.service.Observer.Observable;


/**
 * Class ServiceUser
 */
public class ServiceUser extends Utils implements Observable<UtilizatorChangeData> {

    /**
     * The repository for the users
     */
    private final InMemoryRepository<Long, User> repository;
    private final InMemoryRepository<Long, Friendship> friendships;

    private final Validator<User> validator;

    private final List<Observer<UtilizatorChangeData>> observers = new ArrayList<>();

    /**
     * Constructor for service
     *
     * @param repository    - the repository for the users
     * @param userValidator - validator for User
     */
    public ServiceUser(InMemoryRepository<Long, User> repository, UserValidator userValidator, InMemoryRepository<Long, Friendship> friendships) {
        this.repository = repository;
        this.friendships = friendships;
        this.validator = userValidator;
    }


    public Iterable<Friendship> findAllFriendships() {
        return friendships.findAll();
    }
    /**
     * Method that finds a user by id
     *
     * @param id - id of user
     * @return User with the id given
     */
    public User findOne(Long id) {
        return repository.findOne(id);
    }

    /**
     * Method that returns all users from the container
     *
     * @return the container with entities if they exist, otherwise throws exception
     */
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    /**
     * Method that adds a user to the container
     *
     * @param id        - id of user
     * @param firstname - first name of the user
     * @param lastName  - last name of the user
     */
    public void saveUser(Long id, String firstname, String lastName, Integer age,String username,String password) throws FileNotFoundException {
        User user = new User(firstname, lastName, age,username,password);
        user.setId(id);

        validator.validateID(user);
        validator.validateFirstName(user);
        validator.validateLastName(user);
        validator.validateAge(user);

        UtilizatorChangeData event = new UtilizatorChangeData(ChangeEventType.ADD, user);
        notifyObservers(event);

        repository.save(user);
    }

    /**
     * Method that deletes a user from the container
     *
     * @param id - id of the user that will be deleted
     */
    public void deleteUser(Long id) throws FileNotFoundException {
        User user = repository.findOne(id);

        if(user == null) throw new LackException("No user with this id!\n");
        validator.validateID(user);
        List<Friendship> friendshipList = getFriendships();
        for (Friendship f : friendshipList) {
            if (findOne(f.getId1()).equals(user) || findOne(f.getId2()).equals(user))
                deleteFriend(findOne(f.getId1()).getId(), findOne(f.getId2()).getId());
        }

        notifyObservers(new UtilizatorChangeData(ChangeEventType.DELETE, user));
        repository.delete(id);
    }

    public User findUserByUsername(String username){
        for(User u : repository.findAll()){
            if(u.getUsername().equals(username))
                return u;
        }
        return null;
    }
    public List<Friendship> getFriendships() {
        if (friendships.containerSize() > 0) {
            Iterable<Friendship> friendships = this.friendships.findAll();
            List<Friendship> friendshipList = new ArrayList<>();
            for (Friendship f : friendships)
                friendshipList.add(f);
            return friendshipList;
        }
        return Collections.emptyList();
    }

    /**
     * Method that adds a friendship between two users
     *
     * @param id1 - id of the first user
     * @param id2 - id of the second user
     */
    public void saveFriend(Long id1, Long id2) throws FileNotFoundException {

        if (this.repository.exists(id1) && this.repository.exists(id2)) {
            User user1 = findOne(id1);
            User user2 = findOne(id2);
            if (user1 == user2) {
                throw new DuplicateException("Friendship cannot be formed between same user!\n");
            }
            for (Friendship f : getFriendships()) {
                if (Objects.equals(findOne(f.getId1()), user1) && Objects.equals(findOne(f.getId2()), user2)
                        || Objects.equals(findOne(f.getId1()), user2) && Objects.equals(findOne(f.getId2()), user1)) {

                    throw new DuplicateException("Friendship cannot be formed twice for same users!\n");
                }
            }

            LocalDateTime date = LocalDateTime.now();
            Friendship friendship = new Friendship(id1, id2, date);
            List<Friendship> friendshipList = getFriendships();

            //max id from the list
            Long maxId = 0L;
            for (Friendship f : friendshipList) {
                if (f.getId() > maxId)
                    maxId = f.getId();
            }
            friendship.setId(maxId + 1);

            this.friendships.save(friendship);


        } else throw new FriendshipException("Invalid user id!\n");

    }



    /**
     * Method that deletes a friendship between two users
     *
     * @param id1 - id of the first user
     * @param id2 - id of the second user
     */
    public void deleteFriend(Long id1, Long id2) throws FileNotFoundException {
        if (!repository.exists(id1) || !repository.exists(id2))
            throw new FriendshipException("Invalid user id!\n");
        User user1 = findOne(id1);
        User user2 = findOne(id2);
        if (user1.equals(user2)) {
            throw new DuplicateException("Cannot unfriend the same user!\n");
        }

        List<Friendship> friendshipList = getFriendships();
        for (Friendship f : friendshipList) {
            if (findOne(f.getId1()).equals(user1) && findOne(f.getId2()).equals(user2) ||
                    findOne(f.getId1()).equals(user2) && findOne(f.getId2()).equals(user1)) {
                friendships.delete(f.getId());
                return;
            }
        }
        throw new FriendshipException("These users are not friends!\n");
    }

    /**
     * Method for returning friend list of a user
     *
     * @param id - user id
     * @return List of user friends, else throws exception
     */
    public List<User> friendList(Long id) {
        User user = repository.findOne(id);

        boolean ok = false;
        for (Friendship f : getFriendships()) {
            if (findOne(f.getId1()).equals(user) || findOne(f.getId2()).equals(user)) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            throw new LackException("This user has no friends!\n");
        }
        List<User> userFriends = new ArrayList<>();
        for (Friendship f : getFriendships()) {
            if (findOne(f.getId1()).equals(user))
                userFriends.add(findOne(f.getId2()));
            if (findOne(f.getId2()).equals(user))
                userFriends.add(findOne(f.getId1()));
        }
        return userFriends;
    }

    public Friendship findOneFriendship(Long id) {
        return friendships.findOne(id);
    }


    /**
     * Method that counts the number of communities in the social network,
     * it finds the total number of connected components of the graph
     *
     * @return number of communities
     */
    public int numberOfCommunities() {
        Iterable<User> users = repository.findAll();
        int numberOfUsers = repository.containerSize();
        //System.out.println(numberOfUsers);
        int[][] adjacencyMatrix = new int[2 * numberOfUsers][2 * numberOfUsers];
        int[] visited = new int[numberOfUsers];

        Arrays.fill(visited, 0, numberOfUsers, 0);
        for (int i = 0; i < 2 * numberOfUsers; i++) {
            Arrays.fill(adjacencyMatrix[i], 0, 2 * numberOfUsers, 0);
        }

        iterating(users, adjacencyMatrix);
        int totalNumberOfCommunities = 0;
        for (int i = 0; i < numberOfUsers; i++) {
            if (visited[i] == 0) {
                DFS(i, visited, adjacencyMatrix, numberOfUsers);
                totalNumberOfCommunities++;
            }
        }
        return totalNumberOfCommunities;
    }

    /**
     * Method that finds the most social community of the social network
     *
     * @return list of users that are in the most social community
     */
    public List<User> mostSocialCommunityOfSocialNetwork() {
        Iterable<User> users = repository.findAll();
        int numberOfUsers = repository.containerSize();

        int[][] adjacencyMatrix = new int[2 * numberOfUsers][2 * numberOfUsers];
        int[] visited = new int[numberOfUsers];

        Arrays.fill(visited, 0, numberOfUsers, 0);

        for (int i = 0; i < numberOfUsers; i++) {
            for (int j = 0; j < numberOfUsers; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }

        iterating(users, adjacencyMatrix);
        int totalNumberOfCommunities = 1;
        for (int i = 0; i < numberOfUsers; i++) {
            if (visited[i] == 0) {
                DFS1(i, visited, adjacencyMatrix, numberOfUsers, totalNumberOfCommunities);
                totalNumberOfCommunities++;
            }
        }
        totalNumberOfCommunities--;
        List<User> mostSocialUsers = new ArrayList<>();

        int[] frequency = new int[2 * totalNumberOfCommunities];
        Arrays.fill(frequency, 0, 2 * totalNumberOfCommunities, 0);

        for (int index = 0; index < numberOfUsers; index++) {
            frequency[visited[index]]++;
        }
        int maxim = -1, maxi = -1;
        for (int index = 0; index < totalNumberOfCommunities; index++) {
            if (frequency[index] > maxi) {
                maxi = frequency[index];
                maxim = index;
            }
        }

        for (int i = 0; i < numberOfUsers; i++) {
            if (visited[i] == maxim) {
                for (User user : users) {
                    if (user.getId() == i) {
                        mostSocialUsers.add(user);
                    }
                }
            }
        }
        return mostSocialUsers;

    }

    private void iterating(Iterable<User> users, int[][] adjacencyMatrix) {
        for (User user : users) {
            List<Friendship> friendshipList = getFriendships();
            List<User> user1_friends = new ArrayList<>();
            for (Friendship f : friendshipList) {
                if (findOne(f.getId1()).equals(user)) {
                    user1_friends.add(findOne(f.getId2()));
                }
                if (findOne(f.getId2()).equals(user)) {
                    user1_friends.add(findOne(f.getId1()));
                }
            }
            for (User user1 : user1_friends) {
                adjacencyMatrix[user.getId().intValue()][user1.getId().intValue()] = 1;
            }
        }
    }

    @Override
    public void addObserver(Observer<UtilizatorChangeData> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorChangeData> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorChangeData utilizatorChangeData) {
        observers.forEach(x -> x.update(utilizatorChangeData));
    }

    public void updateFriendshipStatus(Friendship friendship) {
        Friendship friendshipAccepted = new Friendship(friendship.getId1(), friendship.getId2(),friendship.getDate());
        friendshipAccepted.setId(friendship.getId());
        friendshipAccepted.setDate(friendship.getDate());
        friendshipAccepted.setStatus(FriendshipStatus.ACCEPTED);
        friendships.update(friendship, friendshipAccepted);

    }
}
