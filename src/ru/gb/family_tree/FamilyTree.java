package ru.gb.family_tree;

import java.util.ArrayList;
import java.util.List;

public class FamilyTree {
    private long humansId;
    private List<Human> humanList;

    public FamilyTree() {
        this(new ArrayList<>());
    }

    public FamilyTree(List<Human> humanList) {
        this.humanList = humanList;
    }

    public boolean add(Human human) {
        if (human == null) {
            return false;
        }
        if (!humanList.contains(human)) {
            humanList.add(human);
            human.setId(humansId++);

            addToParents(human);
            addToChildren(human);

            return true;
        }

        return false;
    }

    private void addToParents(Human human) {
        for (Human parent : human.getParents()) {
            parent.addChild(human);
        }
    }

    private void addToChildren(Human human) {
        for (Human child : human.getChildren()) {
            child.addParent(human);
        }
    }

    public List<Human> getSiblings(int id) {
        Human human = getById(id);
            if (human == null) {
                return null;
            }
            List<Human> res = new ArrayList<>();
            for (Human parent : human.getParents()) {
                for (Human child : parent.getChildren()) {
                    if (!child.equals(human)) {
                        res.add(child);
                    }
                }
            }
            return res;
    }

    public boolean setDivorce(long humanId1, long humanId2) {
        if (checkId(humanId1) && checkId(humanId2)) {
            Human human1 = getById(humanId1);
            Human human2 = getById(humanId2);
            if (human1.getMarriedPartner() != null && human2.getMarriedPartner() != null) {
                human1.setMarriedPartner(null);
                human2.setMarriedPartner(null);
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean remove(long humansId) {
        if (checkId(humansId)) {
            Human human = getById(humansId);
            return humanList.remove(human);
        }
        return false;
    }

    public List<Human> getByName(String name) {
        List<Human> res = new ArrayList<>();
        for (Human human : humanList) {
            if (human.getName().equals(name)) {
                res.add(human);
            }
        }
        return res;
    }

    public List<Human> getByLastName(String lastName) {
        List<Human> res = new ArrayList<>();
        for (Human human : humanList) {
            if (human.getLastName().equals(lastName)) {
                res.add(human);
            }
        }
        return res;
    }

    public boolean setWedding(long humanId1, long humanId2) {
        if (checkId(humanId1) && checkId(humanId2)) {
            Human human1 = getById(humanId1);
            Human human2 = getById(humanId2);
            if (human1.getMarriedPartner() == null && human2.getMarriedPartner() == null) {
                human1.setMarriedPartner(human2);
                human2.setMarriedPartner(human1);
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean checkId(long id) {
        return id < humansId && id >= 0;
    }

    public Human getById(long id) {
        for (Human human : humanList) {
            if (human.getId() == id) {
                return human;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("В дереве ");
        sb.append(humanList.size());
        sb.append(" объектов: \n");
        for (Human human: humanList) {
            sb.append(human);
            sb.append("\n");
        }
        return sb.toString();
    }
}