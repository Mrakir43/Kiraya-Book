#!/bin/bash

echo "🏠 Setting up Rental Manager Android App..."
echo "============================================"

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  Warning: ANDROID_HOME environment variable is not set"
    echo "   Please install Android SDK and set ANDROID_HOME"
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "⚠️  Warning: Java is not installed or not in PATH"
    echo "   Please install Java 11 or later"
else
    echo "✅ Java found: $(java -version 2>&1 | head -n 1)"
fi

# Make gradlew executable
chmod +x gradlew

echo ""
echo "📱 Rental Manager App Setup Complete!"
echo ""
echo "🚀 Next Steps:"
echo "1. Open Android Studio"
echo "2. Select 'Open an existing Android Studio project'"
echo "3. Navigate to this directory: $(pwd)"
echo "4. Let Android Studio sync the project"
echo "5. Run the app on an emulator or device"
echo ""
echo "📋 Sample Login Credentials:"
echo "   Email: landlord@example.com"
echo "   Password: password123"
echo "   User Type: Landlord"
echo ""
echo "🔧 Troubleshooting:"
echo "   - If you see 404 errors, check your internet connection"
echo "   - Make sure Android SDK is properly installed"
echo "   - Ensure you have Java 11+ installed"
echo ""
echo "📚 For more information, see README.md"