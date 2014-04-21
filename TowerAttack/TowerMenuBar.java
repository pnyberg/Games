import java.awt.*;

public class TowerMenuBar {
	public static final int CANNONS = 0;
	public static final int UPGRADE_CANNON = 1;
	public static final int UPGRADE_SHOCKER = 2;
	public static final int UPGRADE_LASER = 3;

	private final int width = 800;
	private final int height = 200;

	private TowerMenuIcon regularCannonIcon, shockerIcon, laserIcon;
	private TowerMenuIcon regularCannonLevel1Icon, regularCannonLevel2Icon, regularCannonLevel3Icon;
	private TowerMenuIcon shockerLevel1Icon, shockerLevel2Icon, shockerLevel3Icon;
	private TowerMenuIcon laserLevel1Icon, laserLevel2Icon, laserLevel3Icon;

	private int x, y;
	private int state;
	private int cannonLevel;
	private HealthBar healthBar;
	private MoneyBar moneyBar;
	private LevelBar levelBar;
	public TowerStructure currentObject;

	public TowerMenuBar(int x, int y) {
		this.x = x;
		this.y = y;
		state = CANNONS;
		cannonLevel = 0;

		healthBar = new HealthBar(x + 10, y + 110);
		moneyBar = new MoneyBar(x + 10, y + 20);
		levelBar = new LevelBar(x + 10, y + 60);

		// state cannons
		regularCannonIcon = new TowerMenuIcon(x + 100, y + 20, "Regular Cannon");
		shockerIcon = new TowerMenuIcon(x + 230, y + 20, "Shocker");
		laserIcon = new TowerMenuIcon(x + 360, y + 20, "Laser");

		// state upgrade_cannon
		regularCannonLevel1Icon = new TowerMenuIcon(x + 100, y + 20, "Regular Cannon Novice");
		regularCannonLevel2Icon = new TowerMenuIcon(x + 230, y + 20, "Regular Cannon Advance");
		regularCannonLevel3Icon = new TowerMenuIcon(x + 360, y + 20, "Regular Cannon Master");

		// state upgrade_shocker
		shockerLevel1Icon = new TowerMenuIcon(x + 100, y + 20, "Shocker Novice");
		shockerLevel2Icon = new TowerMenuIcon(x + 230, y + 20, "Shocker Advance");
		shockerLevel3Icon = new TowerMenuIcon(x + 360, y + 20, "Shocker Master");

		// state upgrade_laser
		laserLevel1Icon = new TowerMenuIcon(x + 100, y + 20, "Laser Novice");
		laserLevel2Icon = new TowerMenuIcon(x + 230, y + 20, "Laser Advance");
		laserLevel3Icon = new TowerMenuIcon(x + 360, y + 20, "Laser Master");
	}

	// set the state of the menubar
	public void setState(int state) {
		this.state = state;
	}

	// set the state of the menubar for upgrading a specific structure
	public void setState(TowerStructure structure) {
		setState(getUpgradeState(structure));

		currentObject = structure;
		setLevelForBar(currentObject);

		if (structure instanceof TowerCannon)
			checkEnabledIcons(regularCannonLevel1Icon, regularCannonLevel2Icon, regularCannonLevel3Icon);
		else if (structure instanceof TowerShocker)
			checkEnabledIcons(shockerLevel1Icon, shockerLevel2Icon, shockerLevel3Icon);
		else if (structure instanceof TowerLaser)
			checkEnabledIcons(laserLevel1Icon, laserLevel2Icon, laserLevel3Icon);
	}

	// sets the level1, level2 and level3 icons enabled/disabled
	private void checkEnabledIcons(TowerMenuIcon level1, TowerMenuIcon level2, TowerMenuIcon level3) {
		level1.setEnabled(cannonLevel == 1);
		level2.setEnabled(cannonLevel == 2);
		level3.setEnabled(cannonLevel == 3);
	}

	public void takeDamage(TowerBall ball) {
		healthBar.takeDamage(ball);
	}

	public void setLevelForBar(TowerStructure structure) {
		cannonLevel = structure.getLevel();
		levelBar.setLevel(cannonLevel);
	}

	// adds money to the total
	public void addMoney(int cash) {
		moneyBar.money += cash;
	}

	// adds the money-value of the given ball
	public void cash(TowerBall ball) {
		addMoney(ball.moneyValue());
	}

	// help-method to get the upgrade-state
	public int getUpgradeState(TowerStructure structure) {
		if (structure instanceof TowerCannon)
			return UPGRADE_CANNON;
		else if (structure instanceof TowerShocker)
			return UPGRADE_SHOCKER;
		else if (structure instanceof TowerLaser)
			return UPGRADE_LASER;
		else
			return 0;
	}

	public int getHealth() {
		return healthBar.health;
	}

	public int getMoney() {
		return moneyBar.money;
	}

	// if a icon is pressed in the menubar
	public boolean iconPressed(int mousex, int mousey) {
		if (state == CANNONS)
			return pickUpCannon(mousex, mousey);
		else if (state == UPGRADE_CANNON) {
			if (iconClicked(mousex, mousey, regularCannonLevel1Icon))
				upgradeCurrentObject(regularCannonLevel1Icon, regularCannonLevel2Icon);
			else if (iconClicked(mousex, mousey, regularCannonLevel2Icon))
				upgradeCurrentObject(regularCannonLevel2Icon, regularCannonLevel3Icon);
			else if (iconClicked(mousex, mousey, regularCannonLevel3Icon))
				upgradeCurrentObject(regularCannonLevel3Icon);
		} else if (state == UPGRADE_SHOCKER) {
			if (iconClicked(mousex, mousey, shockerLevel1Icon))
				upgradeCurrentObject(shockerLevel1Icon, shockerLevel2Icon);
			else if (iconClicked(mousex, mousey, shockerLevel2Icon))
				upgradeCurrentObject(shockerLevel2Icon, shockerLevel3Icon);
			else if (iconClicked(mousex, mousey, shockerLevel3Icon))
				upgradeCurrentObject(shockerLevel3Icon);
		} else if (state == UPGRADE_LASER) {
			if (iconClicked(mousex, mousey, laserLevel1Icon))
				upgradeCurrentObject(laserLevel1Icon, laserLevel2Icon);
			else if (iconClicked(mousex, mousey, laserLevel2Icon))
				upgradeCurrentObject(laserLevel2Icon, laserLevel3Icon);
			else if (iconClicked(mousex, mousey, laserLevel3Icon))
				upgradeCurrentObject(laserLevel3Icon);
		}

		return false;
	}

	private boolean pickUpCannon(int mousex, int mousey) {
		int xCorrect = 15;
		int yCorrect = 15;
		currentObject = null;

		if (regularCannonIcon.inBounds(mousex, mousey))
			currentObject = new TowerCannon(mousex - xCorrect, mousey - yCorrect);
		else if (shockerIcon.inBounds(mousex, mousey))
			currentObject = new TowerShocker(mousex - xCorrect, mousey - yCorrect);
		else if (laserIcon.inBounds(mousex, mousey))
			currentObject = new TowerLaser(mousex - xCorrect, mousey - yCorrect);
		else
			return false;

		return true;
	}

	// if the given icon was enabled and in clickable bounds
	private boolean iconClicked(int mousex, int mousey, TowerMenuIcon icon) {
		return icon.inBounds(mousex, mousey) && icon.enabled();
	}

	// upgrades the object connected to the currentIcon and makes nextIcon "enabled"
	private boolean upgradeCurrentObject(TowerMenuIcon currentIcon, TowerMenuIcon nextIcon) {
		nextIcon.setEnabled(true);

		return upgradeCurrentObject(currentIcon);
	}

	// used when the last upgrade is done
	private boolean upgradeCurrentObject(TowerMenuIcon currentIcon) {
		currentObject.upgrade();
		currentIcon.setEnabled(false);
		setLevelForBar(currentObject);

		return false;
	}

	public TowerStructure objectPickedUp() {
		return currentObject;
	}

	public void paint(Graphics g) {
		// paints the background of the bar
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);

		healthBar.paint(g);
		moneyBar.paint(g);
		if (state != TowerMenuBar.CANNONS)
			levelBar.paint(g);

		// paints the bar depending on the state
		if (state == TowerMenuBar.CANNONS)
			paintBar(g, regularCannonIcon, shockerIcon, laserIcon);
		else if (state == TowerMenuBar.UPGRADE_CANNON)
			paintBar(g, regularCannonLevel1Icon, regularCannonLevel2Icon, regularCannonLevel3Icon);
		else if (state == TowerMenuBar.UPGRADE_SHOCKER)
			paintBar(g, shockerLevel1Icon, shockerLevel2Icon, shockerLevel3Icon);
		else if (state == TowerMenuBar.UPGRADE_LASER)
			paintBar(g, laserLevel1Icon, laserLevel2Icon, laserLevel3Icon);
	}

	// help-method to paint the bar
	public void paintBar(Graphics g, TowerMenuIcon icon1, TowerMenuIcon icon2, TowerMenuIcon icon3) {
		icon1.paint(g);
		icon2.paint(g);
		icon3.paint(g);
	}
}