import org.sql2o.*;
import java.util.List;

public class Animal {
//Animal class attributes
public String name;
public String endangered;
public int id;

//Animal constructor
public Animal(String name, String endangered) {
  this.name = name;
  this.endangered = endangered;
}
//implement getName()
public String getName() {
  return name;
}
//implement getEndangered()
public String getEndangered() {
    return endangered;
  }
//implement getId()
public int getId() {
    return id;
  }
//implement save()
  public void save() {
      if (name.equals("")) {
        throw new IllegalArgumentException("Please enter a name.");
      }
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO animals (name, endangered) VALUES (:name, :endangered)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("endangered", this.endangered)
          .throwOnMappingFailure(false)
          .executeUpdate()
          .getKey();
      }
    }

//set endangered values
    public void setEndangered(String endangered, String health, String age) {
        if ((!health.equals(EndangeredAnimal.HEALTH_ILL)
          && !health.equals(EndangeredAnimal.HEALTH_OKAY)
          && !health.equals(EndangeredAnimal.HEALTH_HEALTHY))
          || (!age.equals(EndangeredAnimal.AGE_NEWBORN)
          && !age.equals(EndangeredAnimal.AGE_YOUNG)
          && !age.equals(EndangeredAnimal.AGE_ADULT))) {
          throw new IllegalArgumentException("Please choose from ill, okay, healthy or newborn, young, adult");
        }
        try(Connection con = DB.sql2o.open()) {
          String sql = "UPDATE animals SET endangered = :endangered, health = :health, age = :age WHERE id = :id";
          con.createQuery(sql)
            .addParameter("endangered", endangered)
            .addParameter("health", health)
            .addParameter("age", age)
            .addParameter("id", id)
            .executeUpdate();
        }
      }

      //overides the equals boolean had set as false
      @Override
       public boolean equals(Object otherAnimal) {
         if (!(otherAnimal instanceof Animal)) {
           return false;
         } else {
           Animal newAnimal = (Animal) otherAnimal;
           return this.getName().equals(newAnimal.getName());
         }
       }

       public static List<Animal> all() {
          String sql = "select * from animals";
          try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
            .throwOnMappingFailure(false)
            .executeAndFetch(Animal.class);
          }
        }

}
