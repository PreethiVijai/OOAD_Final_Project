package src;
public enum Suits {
   Clubs(0),
   Diamonds(1),
   Hearts(2),
   Spades(3);

   private final int value;

   Suits(final int newValue) {
      value = newValue;
   }

   public int getValue() {
      return value;
   }
}
