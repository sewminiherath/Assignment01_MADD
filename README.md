# 🚀 Smart Travel App

A comprehensive Android travel application built with Kotlin, featuring modern UI design and travel planning capabilities.

## 📱 Features

### 🔐 Authentication
- **Sign In/Sign Up** with email validation
- **Forgot Password** with email reset functionality
- **User Profile** management with photo upload

### 🏠 Home Screen
- **Responsive design** with scrollable content
- **Navigation buttons** for Planner, Explore, Itineraries, Emergency
- **Recommended destinations** with image galleries
- **Bottom navigation** for easy access to all features

### 🗺️ Location Features
- **5 Popular Sri Lankan destinations**:
  - Anuradhapura Sacred City
  - Nallur Kovil
  - Sigiriya Rock Fortress
  - Rawana Waterfall
  - Yapahuwa Fortress
- **Detailed location information** with ratings
- **Nearby restaurants** for each location
- **Add to Planner** functionality

### 📅 Trip Planner
- **Interactive timeline** with multiple destinations
- **Time management** with clock icons
- **Day selection** dropdown
- **Share functionality** with social media integration
- **Check Out** process

### 🔔 Notifications
- **Clean notification center** with modern design
- **No notifications** state with helpful messaging

### 👤 Profile Management
- **User information** display
- **Profile photo** upload capability
- **Emergency contacts** management
- **Settings** and account management
- **Logout** functionality

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: XML Layouts with Material Design
- **Architecture**: MVVM with Repository Pattern
- **Database**: Firebase Firestore (optional)
- **Authentication**: Firebase Auth (optional)
- **Image Handling**: Local drawable resources
- **Navigation**: Intent-based navigation

## 📦 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/newmobileapp/
│   │   ├── activities/          # All Activity classes
│   │   ├── models/              # Data models
│   │   ├── services/            # Firebase services
│   │   └── utils/               # Utility classes
│   ├── res/
│   │   ├── layout/              # XML layouts
│   │   ├── drawable/            # Vector drawables and images
│   │   ├── values/              # Strings, colors, themes
│   │   └── mipmap/              # App icons
│   └── AndroidManifest.xml
├── build.gradle.kts
└── google-services.json
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21 or higher
- Kotlin 1.8.0 or later

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/smart-travel-app.git
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on device/emulator**
   - Connect Android device or start emulator
   - Click "Run" in Android Studio

## 📱 Screenshots

### Home Screen
- Modern dark theme with orange accents
- Scrollable content with destination cards
- Bottom navigation bar

### Authentication
- Clean sign-in/sign-up interface
- Forgot password functionality
- Form validation

### Trip Planner
- Interactive timeline
- Time management features
- Social sharing options

## 🔧 Configuration

### Firebase Setup (Optional)
1. Create a Firebase project
2. Add your app to the project
3. Download `google-services.json`
4. Place it in the `app/` directory
5. Enable Authentication and Firestore in Firebase Console

### Customization
- **Colors**: Edit `app/src/main/res/values/colors.xml`
- **Strings**: Edit `app/src/main/res/values/strings.xml`
- **Themes**: Edit `app/src/main/res/values/themes.xml`

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

For support, email support@smarttravelapp.com or create an issue in this repository.

## 🎯 Roadmap

- [ ] Real-time notifications
- [ ] Offline mode support
- [ ] Multi-language support
- [ ] Advanced trip analytics
- [ ] Social features integration

---

**Built with ❤️ for travelers**
