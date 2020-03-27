
# Declare any extra files needed to compile HillCipher here:
HILLCIPHEREXTRAS=InputHandler.java MatrixHandler.java MessageHandler.java
# Declare any extra files needed to compile HillKeys here:
HILLKEYSEXTRAS=InputHandler.java MatrixHandler.java MessageHandler.java
# Declare any extra files needed to compile HillDecipher here:
HILLDECIPHEREXTRAS=InputHandler.java MatrixHandler.java MessageHandler.java

hill1.zip: HillCipher.java $(HILLCIPHEREXTRAS)
	zip $@ $^

hill2.zip: HillDecipher.java $(HILLDECIPHEREXTRAS) HillKeys.java $(HILLKEYSEXTRAS) HillCipher.java $(HILLCIPHEREXTRAS)
	zip $@ $^

hill3.zip: HillDecipher.java $(HILLDECIPHEREXTRAS) HillKeys.java $(HILLKEYSEXTRAS) HillCipher.java $(HILLCIPHEREXTRAS)
	zip $@ $^
