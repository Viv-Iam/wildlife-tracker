import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;

public class SightingTest {

private Sighting mSighting;
private Sighting mSighting1;
@Before
public void instantiate() {
  mSighting = new Sighting("Jane", "Zone A", 1);
  mSighting1 = new Sighting("Jwaqs", "Zone B", 2);
}
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
 public void sighting_instantiatesCorrectly_true() {
   assertEquals(true, mSighting instanceof Sighting);
 }
 @Test
   public void getRangerName_sightingInstantiatesWithRangerName_Henry() {
     assertEquals("Jane", mSighting.getRangerName());
   }
   @Test
     public void getLocation_sightingInstantiatesWithLocation_ZoneA() {
       assertEquals("Zone A", mSighting.getLocation());
     }

     @Test
       public void getAnimalId_sightingInstantiatesWithAnimalId_1() {
         assertEquals(1, mSighting.getAnimalId());
       }

       @Test
 public void equals_returnsTrueIfNameAndLocationAreSame_true() {
   Sighting testSighting = mSighting;
   Sighting anotherSighting = mSighting;
   assertTrue(testSighting.equals(anotherSighting));
 }

 @Test
  public void save_insertsObjectIntoDatabase_Sighting() {
    mSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(mSighting));
  }

  @Test
  public void save_assignsIdToSighting() {
    mSighting.save();
    Sighting savedSighting = Sighting.all().get(0);
    assertEquals(savedSighting.getId(), mSighting.getId());
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Sighting firstSighting = mSighting;
    firstSighting.save();
    Sighting secondSighting = mSighting1;
    secondSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(firstSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondSighting));
  }

  @Test
public void find_returnsSightingWithSameId_secondSighting() {
  Sighting firstSighting = mSighting;
  firstSighting.save();
  Sighting secondSighting = mSighting1;
  secondSighting.save();
  assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
}
@Test
public void save_recordsTimeOfCreationInDatabase() {
  mSighting.save();
  Timestamp savedSightingTime = Sighting.find(mSighting.getId()).getTimestamp();
  Timestamp rightNow = new Timestamp(new Date().getTime());
  assertEquals(rightNow.getDay(), savedSightingTime.getDay());
}
}
