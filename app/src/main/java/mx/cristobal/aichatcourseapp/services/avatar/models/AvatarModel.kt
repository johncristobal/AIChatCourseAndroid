package mx.cristobal.aichatcourseapp.services.avatar.models

import java.util.Date

data class AvatarModel(
    val avatarId: String,
    val name: String?,
    val characterOption : CharacterOption?,
    val characterAction : CharacterAction?,
    val characterLocation : CharacterLocation?,
    val profileImageName: String?,
    val authorIdval : String?,
    val dateCreatedval : Date?,
    val clickCountval : Int?
) {
    companion object {

        val mocks = listOf(
            AvatarModel(
                avatarId = "avatar_1",
                name = "Alpha",
                characterOption = CharacterOption.MAN,
                characterAction = CharacterAction.SMILING,
                characterLocation = CharacterLocation.PARK,
                profileImageName = "https://picsum.photos/id/1011/600/600",
                authorIdval = "author_1",
                dateCreatedval = Date(),
                clickCountval = 42
            ),
            AvatarModel(
                avatarId = "avatar_2",
                name = "Beta",
                characterOption = CharacterOption.MAN,
                characterAction = CharacterAction.STUDYING,
                characterLocation = CharacterLocation.MUSEUM,
                profileImageName = "https://picsum.photos/id/1027/600/600",
                authorIdval = "author_2",
                dateCreatedval = Date(),
                clickCountval = 128
            ),
            AvatarModel(
                avatarId = "avatar_3",
                name = "Gamma",
                characterOption = CharacterOption.ALIEN,
                characterAction = CharacterAction.WORKING,
                characterLocation = CharacterLocation.SPACE,
                profileImageName = "https://picsum.photos/id/1069/600/600",
                authorIdval = "author_3",
                dateCreatedval = Date(),
                clickCountval = 256
            ),
            AvatarModel(
                avatarId = "avatar_4",
                name = "Delta",
                characterOption = CharacterOption.DOG,
                characterAction = CharacterAction.WALKING,
                characterLocation = CharacterLocation.FOREST,
                profileImageName = "https://picsum.photos/id/237/600/600",
                authorIdval = "author_1",
                dateCreatedval = Date(),
                clickCountval = 15
            ),
            AvatarModel(
                avatarId = "avatar_5",
                name = "Epsilon",
                characterOption = CharacterOption.CAT,
                characterAction = CharacterAction.RELAXING,
                characterLocation = CharacterLocation.CITY,
                profileImageName = "https://picsum.photos/id/1025/600/600",
                authorIdval = "author_4",
                dateCreatedval = Date(),
                clickCountval = 99
            )
        )

        val mock = mocks.first()
    }

    fun description(): String {
        return "A ${this.characterOption?.plural()} that is ${this.characterAction?.name?.lowercase()} in the ${this.characterLocation?.name?.lowercase()}"
    }
}

enum class CharacterOption {
    MAN, WOMAN, ALIEN, DOG, CAT;

    var plural: () -> String = {
        when (this) {
            MAN -> "men"
            WOMAN -> "women"
            ALIEN -> "aliens"
            DOG -> "dogs"
            CAT -> "cats"
        }
    }
}

enum class CharacterAction {
    SMILING, SITTING, EATING, DRINKING, WALKING, SHOPPING, STUDYING, WORKING, RELAXING, FIGHTING, CRYING, SLEEPING;
}

enum class CharacterLocation {
    PARK, MALL, MUSEUM, CITY, FOREST, SPACE;
}